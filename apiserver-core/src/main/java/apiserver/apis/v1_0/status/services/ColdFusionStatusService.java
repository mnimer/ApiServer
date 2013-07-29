package apiserver.apis.v1_0.status.services;

import apiserver.ApiServerConstants;
import apiserver.core.connectors.coldfusion.IColdFusionBridge;
import apiserver.exceptions.ColdFusionException;
import apiserver.exceptions.MessageConfigException;
import org.apache.log4j.Logger;
import org.springframework.integration.Message;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/16/12
 */
public class ColdFusionStatusService
{
    Logger log = Logger.getLogger(ColdFusionStatusService.class);

    public IColdFusionBridge coldFusionBridge;
    public void setColdFusionBridge(IColdFusionBridge coldFusionBridge)
    {
        this.coldFusionBridge = coldFusionBridge;
    }


    public Object healthHandler(Message<?> message) throws ColdFusionException, MessageConfigException
    {
        Map props = (Map)message.getPayload();
        HttpServletRequest request = (HttpServletRequest)props.get(ApiServerConstants.HTTP_REQUEST);


        try
        {
            long start = System.currentTimeMillis();
            String cfcPath = "/WEB-INF/cfservices-inf/components/v1_0/api-status.cfc";
            String method = "health";
            String arguments = "";
            Map cfcResult = (Map)coldFusionBridge.invoke(cfcPath, method, arguments, null);
            long end = System.currentTimeMillis();

            // Could be a HashMap or a MultiValueMap
            Map payload = (Map) message.getPayload();
            payload.putAll(cfcResult);
            payload.put("executionTime", end-start);
            return payload;
        }
        catch (Throwable e)
        {
            log.error(e);
            throw new RuntimeException(e);
        }
    }
}
