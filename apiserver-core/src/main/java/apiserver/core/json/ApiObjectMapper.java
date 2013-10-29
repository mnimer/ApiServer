package apiserver.core.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikenimer
 * Date: 10/28/13
 */
public class ApiObjectMapper extends ObjectMapper
{
    private final Logger log = LoggerFactory.getLogger(ApiObjectMapper.class);


    public ApiObjectMapper()
    {
        super();
        this.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
