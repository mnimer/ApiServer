package apiserver.apis.v1_0.images.service.cache;

import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.apis.v1_0.images.wrappers.CachedImage;
import apiserver.exceptions.FactoryException;
import apiserver.exceptions.MessageConfigException;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * User: mnimer
 * Date: 9/27/12
 */
public class ImageCacheService
{

    @Autowired
    public ImageConfigMBeanImpl imageConfigMBean;


    public Object checkCache(Message<?> message) throws MessageConfigException
    {
        Map props = (Map)message.getPayload();

        Object key = props.get(ImageConfigMBeanImpl.KEY);
        if( key != null )
        {
            try
            {
                Element file = imageConfigMBean.getCache().get(key);
                if( file != null )
                {
                    props.put( ImageConfigMBeanImpl.FILE, file.getObjectValue() );
                }
            }
            catch (FactoryException fe)
            {
                fe.printStackTrace();
                //todo LOG
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

        Object file = props.get(ImageConfigMBeanImpl.FILE);
        Integer timeout = (Integer)props.get(ImageConfigMBeanImpl.TIME_TO_LIVE);

        if( file == null )
        {
            throw new MessageConfigException( MessageConfigException.MISSING_PROPERTY );
        }

        // todo use a wrapper so we can add per file timeout
        CachedImage cachedImage = new CachedImage( (CommonsMultipartFile)file );


        Element element = new Element(key, cachedImage );
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
        Element file = imageConfigMBean.getCache().get(key);

        props.put(key, file.getObjectValue());

        return props;
    }

}
