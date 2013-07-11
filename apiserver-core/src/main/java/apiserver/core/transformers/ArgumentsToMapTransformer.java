package apiserver.core.transformers;

import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.Payload;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mikenimer
 * Date: 7/8/13
 */
public class ArgumentsToMapTransformer
{
    public final Logger log = LoggerFactory.getLogger(ArgumentsToMapTransformer.class);

    public Map<String, Object> processArguments(Message<?> message)
    {
        Object payload = message.getPayload();
        Map<String, Object> args = new HashMap();

        if( payload instanceof String )
        {
            args.put(ImageConfigMBeanImpl.KEY, payload);
        }
        else if( payload instanceof MultipartFile)
        {
            args.put(ImageConfigMBeanImpl.FILE, payload);
        }
        else if( payload instanceof File)
        {
            args.put(ImageConfigMBeanImpl.FILE, payload);
        }
        else
        {
            args.putAll((Map)payload);
        }

        return args;
    }
}
