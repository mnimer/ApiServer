package apiserver.apis.v1_0.images.service.cache;

import apiserver.apis.v1_0.images.ImageConfigMBean;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.apis.v1_0.images.wrappers.CachedImage;
import apiserver.exceptions.FactoryException;
import apiserver.exceptions.MessageConfigException;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
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
        Map props = (Map)message.getPayload();

        Object key = props.get(ImageConfigMBeanImpl.KEY);
        if( key != null )
        {
            try
            {
                Element cachedElement = imageConfigMBean.getCache().get(key);
                if( cachedElement != null )
                {
                    Map cachedProperties = (Map)cachedElement.getObjectValue();
                    props.putAll(cachedProperties);
                }
            }
            catch (FactoryException fe)
            {
                fe.printStackTrace();
                //todo LOG
            }
        }
        else
        {
            // not in cache, (file upload - form post) so convert it as if it was cached so the code after this
            // works the same.
            Object fileObj = props.get(ImageConfigMBeanImpl.FILE);
            if( fileObj instanceof CommonsMultipartFile )
            {
                CommonsMultipartFile file = ((CommonsMultipartFile)props.get(ImageConfigMBeanImpl.FILE));

                Map cachedProperties = getFileProperties(file);
                props.putAll(cachedProperties);
            }
            else if( fileObj instanceof File )
            {
                Map cachedProperties = getFileProperties((File)fileObj);
                props.putAll(cachedProperties);
            }
        }

        if( !props.containsKey( ImageConfigMBeanImpl.FILE) )
        {
             throw new MessageConfigException( MessageConfigException.MISSING_PROPERTY );
        }

        return props;
    }


    public Object addToCache(Message<?> message) throws FactoryException, MessageConfigException, IOException
    {
        Map props = (Map)message.getPayload();
        String key = UUID.randomUUID().toString();

        CommonsMultipartFile file = (CommonsMultipartFile)props.get(ImageConfigMBeanImpl.FILE);
        Integer timeout = (Integer)props.get(ImageConfigMBeanImpl.TIME_TO_LIVE);

        if( file == null )
        {
            throw new MessageConfigException( MessageConfigException.MISSING_PROPERTY );
        }


        // get & save file properties
        Map cachedProperties = getFileProperties(file);


        Element element = new Element(key, cachedProperties );
        if( timeout == 0)
        {
            element.setEternal(true);
        }
        else
        {
            element.setTimeToLive(timeout);
        }


        imageConfigMBean.getCache().put(element);
        props.put(ImageConfigMBeanImpl.KEY, key);


        return props;
    }


    public Object getFromCache(Message<?> message) throws FactoryException
    {
        Map props = (Map)message.getPayload();
        String key = (String)props.get(ImageConfigMBeanImpl.KEY);

        // todo use a wrapper so we can add per file timeout
        Element cachedElement = imageConfigMBean.getCache().get(key);

        Map cachedProperties = (Map) cachedElement.getObjectValue();

        props.put(ImageConfigMBeanImpl.RESULT, cachedProperties.get(ImageConfigMBeanImpl.FILE));

        return props;
    }




    private Map getFileProperties(CommonsMultipartFile file) throws IOException
    {
        //BufferedImage cachedImage = ImageIO.read(  file.getInputStream()  );
        CachedImage cachedImage = new CachedImage(file);
        cachedImage.setFileName(file.getOriginalFilename());
        cachedImage.setSize( file.getSize() );
        cachedImage.setContentType( file.getContentType() );

        Map cachedProperties = new HashMap();
        cachedProperties.put(ImageConfigMBeanImpl.FILE, cachedImage);
        cachedProperties.put(ImageConfigMBeanImpl.CONTENT_TYPE, file.getContentType());
        cachedProperties.put(ImageConfigMBeanImpl.ORIGINAL_FILE_NAME, file.getOriginalFilename());
        cachedProperties.put(ImageConfigMBeanImpl.NAME, file.getName());
        cachedProperties.put(ImageConfigMBeanImpl.SIZE, file.getSize());

        return cachedProperties;
    }

    private Map getFileProperties(File file) throws IOException
    {
        //BufferedImage cachedImage = ImageIO.read(  file.getInputStream()  );
        CachedImage cachedImage = new CachedImage(file);

        Map cachedProperties = new HashMap();
        cachedProperties.put(ImageConfigMBeanImpl.FILE, cachedImage);
        cachedProperties.put(ImageConfigMBeanImpl.NAME, file.getName());

        return cachedProperties;
    }

}
