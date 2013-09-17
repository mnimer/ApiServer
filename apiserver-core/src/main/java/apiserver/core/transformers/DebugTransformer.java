package apiserver.core.transformers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * A simple transformer that we can inject into a flow and hit breakpoints
 * User: mikenimer
 * Date: 7/6/13
 */
@Component
public class DebugTransformer
{
    public final Logger log = LoggerFactory.getLogger(DebugTransformer.class);


    @Transformer
    public Message<Map> transform(Message<Map> message)
    {
        return message;
    }
}
