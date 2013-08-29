package apiserver.apis.v1_0.status.services;

import apiserver.core.connectors.coldfusion.IColdFusionBridge;
import apiserver.exceptions.ColdFusionException;
import apiserver.exceptions.MessageConfigException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

/**
 * User: mnimer
 * Date: 9/16/12
 */
public class ColdFusionStatusService
{
    Logger log = Logger.getLogger(ColdFusionStatusService.class);

    @Autowired
    public IColdFusionBridge coldFusionBridge;
    public void setColdFusionBridge(IColdFusionBridge coldFusionBridge)
    {
        this.coldFusionBridge = coldFusionBridge;
    }


    public Object healthHandler(Message<?> message) throws ColdFusionException, MessageConfigException
    {

        try
        {
            String cfcPath = "api-status.cfc?method=health";
            String method = "GET";
            String arguments = "";
            Object cfcResult = coldFusionBridge.invoke(cfcPath, method, null);

            Message<?> _message = MessageBuilder.withPayload(cfcResult).copyHeaders(message.getHeaders()).build();
            return _message;

        }
        catch (Throwable e)
        {
            log.error(e);
            throw new RuntimeException(e);
        }
    }
}
