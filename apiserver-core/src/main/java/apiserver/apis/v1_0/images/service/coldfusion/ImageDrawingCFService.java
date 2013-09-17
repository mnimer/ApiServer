package apiserver.apis.v1_0.images.service.coldfusion;

import apiserver.apis.v1_0.images.ImageConfigMBean;
import apiserver.core.connectors.coldfusion.IColdFusionBridge;
import apiserver.exceptions.ColdFusionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/18/12
 */
public class ImageDrawingCFService
{

    private static String cfcPath;

    @Autowired
    private ImageConfigMBean imageConfigMBean;

    @Autowired
    public IColdFusionBridge coldFusionBridge;
    public void setColdFusionBridge(IColdFusionBridge coldFusionBridge)
    {
        this.coldFusionBridge = coldFusionBridge;
    }

    public Object imageBorderHandler(Message<?> message) throws ColdFusionException
    {
        Map props = (Map)message.getPayload();

        try
        {
            cfcPath = imageConfigMBean.getImageBorderPath();
            String method = imageConfigMBean.getImageBorderMethod();
            Map<String, Object> methodArgs = new HashMap();
            Map cfcResult = (Map)coldFusionBridge.invoke(cfcPath, method, methodArgs);


            Message<?> _message = MessageBuilder.withPayload(cfcResult).copyHeaders(message.getHeaders()).build();
            return _message;
        }
        catch (Throwable e)
        {
            e.printStackTrace(); //todo use logging library
            throw new RuntimeException(e);
        }
    }


    public Object imageDrawTextHandler(Message<?> message) throws ColdFusionException
    {
        Map props = (Map)message.getPayload();

        try
        {
            cfcPath = imageConfigMBean.getImageTextPath();
            String method = imageConfigMBean.getImageTextMethod();
            Map<String, Object> methodArgs = coldFusionBridge.extractPropertiesFromPayload(props);
            Map cfcResult = (Map)coldFusionBridge.invoke(cfcPath, method, methodArgs);


            Message<?> _message = MessageBuilder.withPayload(cfcResult).copyHeaders(message.getHeaders()).build();
            return _message;
        }
        catch (Throwable e)
        {
            e.printStackTrace(); //todo use logging library
            throw new RuntimeException(e);
        }
    }
}
