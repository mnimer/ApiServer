package apiserver.apis.v1_0.status.bus.service;

import org.springframework.integration.Message;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/15/12
 */
public class StatusBusService
{
    public Object noOpHandler(Message<?> message)
    {
        return null;
    }


    public Object pingHandler(Message<?> message)
    {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("status", "ok");
        props.put("timestamp", new Date().getTime());


        // Could be a HashMap or a MultiValueMap
        Map payload = (Map) message.getPayload();
        payload.putAll(props);
        return payload;
    }
}
