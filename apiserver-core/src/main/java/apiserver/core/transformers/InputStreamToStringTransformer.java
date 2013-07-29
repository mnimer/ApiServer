package apiserver.core.transformers;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.transformer.AbstractPayloadTransformer;

import java.io.InputStream;

/**
 * User: mikenimer
 * Date: 7/28/13
 */
public class InputStreamToStringTransformer  extends AbstractPayloadTransformer<Object, String>
{
    public final Logger log = LoggerFactory.getLogger(InputStreamToStringTransformer.class);

    @Override
    protected String transformPayload(Object payload) throws Exception
    {
        return IOUtils.toString((InputStream)payload);
    }

}
