package apiserver.apis.v1_0.images.service.coldfusion;

import apiserver.apis.v1_0.images.models.images.ImageInfoModel;
import apiserver.core.connectors.coldfusion.IColdFusionBridge;
import apiserver.exceptions.ColdFusionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import java.util.Map;

/**
 * User: mnimer
 * Date: 9/18/12
 */
public class ImageInfoService
{
    private static String cfcPath;


    @Autowired
    public IColdFusionBridge coldFusionBridge;
    public void setColdFusionBridge(IColdFusionBridge coldFusionBridge)
    {
        this.coldFusionBridge = coldFusionBridge;
    }

    public Object execute(Message<?> message) throws ColdFusionException
    {

        ImageInfoModel props = (ImageInfoModel)message.getPayload();

        try
        {
            cfcPath = "api-image.cfc?method=imageInfo";
            String method = "GET";
            // extract properties
            Map<String, Object> methodArgs = coldFusionBridge.extractPropertiesFromPayload(props);
            methodArgs.put("image", props.getFile());

            // execute
            Object cfcResult = coldFusionBridge.invoke(cfcPath, method, methodArgs);

            Message<?> _message = MessageBuilder.withPayload(cfcResult).copyHeaders(message.getHeaders()).build();
            return _message;
        }
        catch (Throwable e)
        {
            e.printStackTrace(); //todo use logging library
            throw new RuntimeException(e);
        }
        finally
        {

        }
    }




}
