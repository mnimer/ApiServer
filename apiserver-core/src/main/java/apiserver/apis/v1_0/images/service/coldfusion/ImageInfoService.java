package apiserver.apis.v1_0.images.service.coldfusion;

import apiserver.ApiServerConstants;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.apis.v1_0.images.models.images.ImageInfoModel;
import apiserver.apis.v1_0.images.wrappers.CachedImage;
import apiserver.core.connectors.coldfusion.IColdFusionBridge;
import apiserver.exceptions.ColdFusionException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
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
        Map<String, Object> methodArgs = new HashMap<String, Object>();

        try
        {
            long start = System.currentTimeMillis();

            cfcPath = "/apiserver-inf/components/v1_0/api-image.cfc?method=imageInfo";
            String method = "GET";
            String arguments = "";
            // extract properties
            methodArgs.put("image", props.getFile());

            // execute
            Object cfcResult = coldFusionBridge.invoke(cfcPath, method, arguments, methodArgs);

            //String _transformedPayload = IOUtils.toString((InputStream)cfcResult);
            Message<?> _message = MessageBuilder.withPayload(cfcResult).copyHeaders(message.getHeaders()).build();

            return _message;

            /**
            long end = System.currentTimeMillis();


            // Could be a HashMap or a MultiValueMap
            Map payload = (Map) message.getPayload();
            payload.clear();
            //payload.putAll(cfcResult);


            Map cfData = new HashMap();
            cfData.put("executiontime", end - start);
            payload.put("coldfusion", cfData);


            return payload;
             **/

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
