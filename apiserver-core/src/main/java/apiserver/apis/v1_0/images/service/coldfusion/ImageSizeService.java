package apiserver.apis.v1_0.images.service.coldfusion;

import apiserver.ApiServerConstants;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.apis.v1_0.images.wrappers.CachedImage;
import apiserver.core.connectors.coldfusion.IColdFusionBridge;
import apiserver.exceptions.ColdFusionException;
import org.springframework.integration.Message;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/18/12
 */
public class ImageSizeService
{
    private static String cfcPath;


    public IColdFusionBridge coldFusionBridge;
    public void setColdFusionBridge(IColdFusionBridge coldFusionBridge)
    {
        this.coldFusionBridge = coldFusionBridge;
    }

    public Object imageSizeHandler(Message<?> message) throws ColdFusionException
    {

        Map props = (Map)message.getPayload();
        HttpServletRequest request = (HttpServletRequest)props.get(ApiServerConstants.HTTP_REQUEST);

        try
        {
            long start = System.currentTimeMillis();

            cfcPath = "/WEB-INF/cfservices-inf/components/v1_0/api-image.cfc";
            String method = "rotateImage";
            // extract properties
            CachedImage cachedImage = (CachedImage)props.get(ImageConfigMBeanImpl.FILE);
            Object[] methodArgs = { cachedImage.getFileBytes() };
            // execute
            Map cfcResult = (Map)coldFusionBridge.invoke(cfcPath, method, methodArgs, request);

            long end = System.currentTimeMillis();


            // Could be a HashMap or a MultiValueMap
            Map payload = (Map) message.getPayload();
            payload.clear();
            payload.putAll(cfcResult);


            Map cfData = new HashMap();
            cfData.put("executiontime", end - start);
            payload.put("coldfusion", cfData);


            return payload;

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
