package apiserver.core.transformers;

import apiserver.apis.v1_0.images.wrappers.CachedImage;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.message.GenericMessage;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * User: mikenimer
 * Date: 7/6/13
 */
@Component
public class HttpPostPropertyTransformer
{
    public final Logger log = LoggerFactory.getLogger(HttpPostPropertyTransformer.class);


    @Transformer
    public Message<Map> transform(Message<Map> message) throws IOException
    {
        Map newArgs = new HashMap<String, Object>();

        Object payload = message.getPayload();
        if( payload instanceof  Map )
        {
            Set keys = ((Map)payload).keySet();
            for (Object key : keys)
            {
                Object item = ((Map)payload).get(key);
                if( item instanceof CachedImage )
                {
                    newArgs.put( key.toString().toUpperCase(), new FileSystemResource( ((CachedImage)item).getFile()) );
                }
                else
                {
                    newArgs.put( key.toString().toUpperCase(), item);
                }
            }

            message.getPayload().clear();
            message.getPayload().putAll(newArgs);
        }

        return message;
    }

}
