package apiserver.apis.v1_0.images.service.cache;

import apiserver.apis.v1_0.images.ImageConfigMBean;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.apis.v1_0.images.models.ImageModel;
import apiserver.apis.v1_0.images.models.filters.MaskModel;
import apiserver.apis.v1_0.images.wrappers.CachedImage;
import apiserver.exceptions.FactoryException;
import apiserver.exceptions.MessageConfigException;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * User: mnimer
 * Date: 9/27/12
 */
public class ImageCacheService
{

    @Autowired
    private ImageConfigMBean imageConfigMBean;


    public Object checkCache(Message<?> message) throws MessageConfigException, IOException
    {
        ImageModel payload = (ImageModel) message.getPayload();
        return checkCache(payload);
    }


    //todo, the real logic should be against the typed class, not the map (remove this once all filters are converted)
    public Object checkCache(Map payload) throws MessageConfigException, IOException
    {
        return payload;
    }


    public Object checkCache(ImageModel props) throws MessageConfigException, IOException
    {
        // pull out the cached image, or add the new image to cache
        checkCacheId(props);

        // if this is a MaskFilterArg, we might need to pull the mask property from cache too.
        checkMask(props);

        return props;
    }


    private void checkCacheId(ImageModel props) throws IOException, MessageConfigException
    {
        String key = props.getCacheId();
        // pull the cached image from cache, or put the image in cache, for next time.
        if (key != null)
        {
            try
            {
                Element cachedElement = imageConfigMBean.getCache().get(key);
                if (cachedElement != null)
                {
                    Map cachedProperties = (Map) cachedElement.getObjectValue();
                    props.setFile( cachedProperties.get(ImageConfigMBeanImpl.FILE) );
                }
            } catch (FactoryException fe)
            {
                fe.printStackTrace();
                //todo LOG
            }
        }
        else if (props.getFile() != null)
        {
            Object file = props.getFile();

            // replace File or Multipart File with CachedImage wrapped version
            if( file instanceof MultipartFile )
            {
                Map cachedProperties = getFileProperties(  (MultipartFile)props.getFile() );
                props.setFile( cachedProperties.get(ImageConfigMBeanImpl.FILE) );
            } else if( file instanceof File )
            {
                Map cachedProperties = getFileProperties(  (File)props.getFile() );
                props.setFile( cachedProperties.get(ImageConfigMBeanImpl.FILE) );
            }
        }
        else if ( props.getFile() == null  )
        {
            throw new MessageConfigException(MessageConfigException.MISSING_PROPERTY);
        }
    }


    /**
     * Check the cache for MaskFilterArg mask property (if it's a String cache ID)
     * @param props
     * @throws IOException
     */
    private void checkMask(ImageModel props) throws IOException
    {
        String maskKey = null;
        if(props instanceof MaskModel)
        {
            if( ((MaskModel)props).getMask() instanceof String )
            {
                maskKey = (String)((MaskModel)props).getMask();

                try
                {
                    Element cachedElement = imageConfigMBean.getCache().get(maskKey);
                    if (cachedElement != null)
                    {
                        Map cachedProperties = (Map) cachedElement.getObjectValue();
                        ((MaskModel) props).setMask(  ((CachedImage)cachedProperties.get(ImageConfigMBeanImpl.FILE)) );
                    }
                } catch (FactoryException fe)
                {
                    fe.printStackTrace();
                    //todo LOG
                }

            }
            else if (props.getFile() != null)
            {
                Object file = props.getFile();

                // replace File or Multipart File with CachedImage wrapped version
                if( file instanceof MultipartFile )
                {
                    Map cachedProperties = getFileProperties((MultipartFile)file);
                    props.setFile( cachedProperties.get(ImageConfigMBeanImpl.FILE) );
                }else{
                    Map cachedProperties = getFileProperties((File)file);
                    props.setFile( cachedProperties.get(ImageConfigMBeanImpl.FILE) );
                }
            }
        }
    }




    public Object addToCache(Message<?> message) throws FactoryException, MessageConfigException, IOException
    {
        Map props = (Map) message.getPayload();
        String key = UUID.randomUUID().toString();

        CommonsMultipartFile file = (CommonsMultipartFile) props.get(ImageConfigMBeanImpl.FILE);
        Integer timeout = (Integer) props.get(ImageConfigMBeanImpl.TIME_TO_LIVE);

        if (file == null)
        {
            throw new MessageConfigException(MessageConfigException.MISSING_PROPERTY);
        }


        // get & save file properties
        Map cachedProperties = getFileProperties(file);


        Element element = new Element(key, cachedProperties);
        if (timeout == 0)
        {
            element.setEternal(true);
        } else
        {
            element.setTimeToLive(timeout);
        }


        imageConfigMBean.getCache().put(element);
        props.put(ImageConfigMBeanImpl.KEY, key);


        return props;
    }




    public Object getFromCache(Message<?> message) throws FactoryException
    {
        Map props = (Map) message.getPayload();
        String key = (String) props.get(ImageConfigMBeanImpl.KEY);

        // todo use a wrapper so we can add per file timeout
        Element cachedElement = imageConfigMBean.getCache().get(key);

        Map cachedProperties = (Map) cachedElement.getObjectValue();

        props.put(ImageConfigMBeanImpl.RESULT, cachedProperties.get(ImageConfigMBeanImpl.FILE));

        return props;
    }


    public Object deleteFromCache(Message<?> message) throws FactoryException
    {
        Map props = (Map) message.getPayload();
        String key = (String) props.get(ImageConfigMBeanImpl.KEY);

        try
        {
            imageConfigMBean.getCache().remove(key);
            props.put(ImageConfigMBeanImpl.RESULT, Boolean.TRUE);
        }catch (Exception ex)
        {

        }
        return props;
    }


    /**
     * pull out the common file properties from a MultipartFile
     * @param file
     * @return
     * @throws IOException
     */
    private Map getFileProperties(MultipartFile file) throws IOException
    {
        //BufferedImage cachedImage = ImageIO.read(  file.getInputStream()  );
        CachedImage cachedImage = new CachedImage(file);
        cachedImage.setFileName(file.getOriginalFilename());
        cachedImage.setSize(file.getSize());
        cachedImage.setContentType(file.getContentType());

        Map cachedProperties = new HashMap();
        cachedProperties.put(ImageConfigMBeanImpl.FILE, cachedImage);
        cachedProperties.put(ImageConfigMBeanImpl.CONTENT_TYPE, file.getContentType());
        cachedProperties.put(ImageConfigMBeanImpl.ORIGINAL_FILE_NAME, file.getOriginalFilename());
        cachedProperties.put(ImageConfigMBeanImpl.NAME, file.getName());
        cachedProperties.put(ImageConfigMBeanImpl.SIZE, file.getSize());

        return cachedProperties;
    }


    /**
     * pull out the common file properties from a File
     * @param file
     * @return
     * @throws IOException
     */
    private Map getFileProperties(File file) throws IOException
    {
        //BufferedImage cachedImage = ImageIO.read(  file.getInputStream()  );
        CachedImage cachedImage = new CachedImage(file);

        Map cachedProperties = new HashMap();
        cachedProperties.put(ImageConfigMBeanImpl.FILE, cachedImage);
        cachedProperties.put(ImageConfigMBeanImpl.NAME, file.getName());

        String extension = file.getName().substring(file.getName().lastIndexOf('.') + 1, file.getName().length());
        cachedImage.setContentType("image/" + extension);
        cachedProperties.put(ImageConfigMBeanImpl.CONTENT_TYPE, "image/" + extension);

        return cachedProperties;
    }

}
