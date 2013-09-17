package apiserver.apis.v1_0.pdf.service.cache;

import apiserver.apis.v1_0.pdf.PdfConfigMBean;
import apiserver.apis.v1_0.pdf.PdfConfigMBeanImpl;
import apiserver.apis.v1_0.pdf.models.PdfModel;
import apiserver.apis.v1_0.pdf.wrappers.CachedFile;
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
public class PdfCacheService
{

    @Autowired
    private PdfConfigMBean pdfConfigMBean;


    public Object checkCache(Message<?> message) throws MessageConfigException, IOException
    {
        PdfModel payload = (PdfModel) message.getPayload();
        return checkCache(payload);
    }


    //todo, the real logic should be against the typed class, not the map (remove this once all filters are converted)
    public Object checkCache(Map payload) throws MessageConfigException, IOException
    {
        return payload;
    }


    public Object checkCache(PdfModel props) throws MessageConfigException, IOException
    {
        // pull out the cached item, or add the new item to cache
        checkCacheId(props);

        return props;
    }


    private void checkCacheId(PdfModel props) throws IOException, MessageConfigException
    {
        String key = props.getCacheId();
        // pull the cached item from cache, or put the item in cache, for next time.
        if (key != null)
        {
            try
            {
                Element cachedElement = pdfConfigMBean.getCache().get(key);
                if (cachedElement != null)
                {
                    Map cachedProperties = (Map) cachedElement.getObjectValue();
                    props.setFile( cachedProperties.get(PdfConfigMBeanImpl.FILE) );
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

            // replace File or Multipart File with CachedFile wrapped version
            if( file instanceof MultipartFile )
            {
                Map cachedProperties = getFileProperties(  (MultipartFile)props.getFile() );
                props.setFile( cachedProperties.get(PdfConfigMBeanImpl.FILE) );
            } else if( file instanceof File )
            {
                Map cachedProperties = getFileProperties(  (File)props.getFile() );
                props.setFile( cachedProperties.get(PdfConfigMBeanImpl.FILE) );
            }
        }
        else if ( props.getFile() == null  )
        {
            throw new MessageConfigException(MessageConfigException.MISSING_PROPERTY);
        }
    }


    public Object addToCache(Message<?> message) throws FactoryException, MessageConfigException, IOException
    {
        Map props = (Map) message.getPayload();
        String key = UUID.randomUUID().toString();

        CommonsMultipartFile file = (CommonsMultipartFile) props.get(PdfConfigMBeanImpl.FILE);
        Integer timeout = (Integer) props.get(PdfConfigMBeanImpl.TIME_TO_LIVE);

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


        pdfConfigMBean.getCache().put(element);
        props.put(PdfConfigMBeanImpl.KEY, key);


        return props;
    }




    public Object getFromCache(Message<?> message) throws FactoryException
    {
        Map props = (Map) message.getPayload();
        String key = (String) props.get(PdfConfigMBeanImpl.KEY);

        // todo use a wrapper so we can add per file timeout
        Element cachedElement = pdfConfigMBean.getCache().get(key);

        Map cachedProperties = (Map) cachedElement.getObjectValue();

        props.put(PdfConfigMBeanImpl.RESULT, cachedProperties.get(PdfConfigMBeanImpl.FILE));

        return props;
    }


    public Object deleteFromCache(Message<?> message) throws FactoryException
    {
        Map props = (Map) message.getPayload();
        String key = (String) props.get(PdfConfigMBeanImpl.KEY);

        try
        {
            pdfConfigMBean.getCache().remove(key);
            props.put(PdfConfigMBeanImpl.RESULT, Boolean.TRUE);
        }catch (Exception ex)
        {

        }
        return props;
    }


    /**
     * pull out the common file properties from a MultipartFile
     * @param file
     * @return
     * @throws java.io.IOException
     */
    private Map getFileProperties(MultipartFile file) throws IOException
    {
        CachedFile cachedFile = new CachedFile(file);
        cachedFile.setFileName(file.getOriginalFilename());
        cachedFile.setSize(file.getSize());
        cachedFile.setContentType(file.getContentType());

        Map cachedProperties = new HashMap();
        cachedProperties.put(PdfConfigMBeanImpl.FILE, cachedFile);
        cachedProperties.put(PdfConfigMBeanImpl.CONTENT_TYPE, file.getContentType());
        cachedProperties.put(PdfConfigMBeanImpl.ORIGINAL_FILE_NAME, file.getOriginalFilename());
        cachedProperties.put(PdfConfigMBeanImpl.NAME, file.getName());
        cachedProperties.put(PdfConfigMBeanImpl.SIZE, file.getSize());

        return cachedProperties;
    }


    /**
     * pull out the common file properties from a File
     * @param file
     * @return
     * @throws java.io.IOException
     */
    private Map getFileProperties(File file) throws IOException
    {
        CachedFile cachedFile = new CachedFile(file);

        Map cachedProperties = new HashMap();
        cachedProperties.put(PdfConfigMBeanImpl.FILE, cachedFile);
        cachedProperties.put(PdfConfigMBeanImpl.NAME, file.getName());
        cachedProperties.put(PdfConfigMBeanImpl.CONTENT_TYPE, cachedFile.getContentType());

        if( cachedFile.getContentType() == null )
        {
            String extension = file.getName().substring(file.getName().lastIndexOf('.') + 1, file.getName().length());
            cachedFile.setContentType("application/" + extension);
            cachedProperties.put(PdfConfigMBeanImpl.CONTENT_TYPE, "application/" + extension);
        }
        return cachedProperties;
    }

}
