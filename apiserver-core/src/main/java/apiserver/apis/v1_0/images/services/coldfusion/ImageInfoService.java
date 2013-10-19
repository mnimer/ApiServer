package apiserver.apis.v1_0.images.services.coldfusion;

import apiserver.apis.v1_0.images.ImageConfigMBean;
import apiserver.apis.v1_0.images.models.images.FileInfoModel;
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
    @Autowired
    public ImageConfigMBean imageConfigMBean;

    @Autowired
    public IColdFusionBridge coldFusionBridge;

    public void setColdFusionBridge(IColdFusionBridge coldFusionBridge)
    {
        this.coldFusionBridge = coldFusionBridge;
    }

    public Object execute(Message<?> message) throws ColdFusionException
    {

        FileInfoModel props = (FileInfoModel)message.getPayload();

        try
        {
            String cfcPath = imageConfigMBean.getImageInfoPath();
            String method = imageConfigMBean.getImageInfoMethod();
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