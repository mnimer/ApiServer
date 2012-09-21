package apiserver.apis.v1_0.status.services;

import org.springframework.integration.Message;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/15/12
 */
public class StatusService
{


    public Message<?> healthHandler(Message<?> message)
    {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("status", "ok");
        props.put("timestamp", new Date().getTime());


        // Could be a HashMap or a MultiValueMap
        Map payload = (Map) message.getPayload();
        payload.putAll(props);
        return message;
    }
}
