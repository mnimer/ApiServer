package apiserver.apis.v1_0.images.service.coldfusion;

import apiserver.ApiServerConstants;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.apis.v1_0.images.wrappers.CachedImage;
import apiserver.core.connectors.coldfusion.IColdFusionBridge;
import apiserver.exceptions.ColdFusionException;
import apiserver.exceptions.MessageConfigException;
import org.springframework.integration.Message;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/18/12
 */
public class ImageManipulationCFService
{

    private static String cfcPath;


    public IColdFusionBridge coldFusionBridge;
    public void setColdFusionBridge(IColdFusionBridge coldFusionBridge)
    {
        this.coldFusionBridge = coldFusionBridge;
    }

    public Object imageRotateHandler(Message<?> message) throws ColdFusionException
    {
        Map props = (Map)message.getPayload();
        HttpServletRequest request = (HttpServletRequest)props.get(ApiServerConstants.HTTP_REQUEST);

        if( cfcPath == null )
        {
            if( request == null )
            {
                throw new RuntimeException(MessageConfigException.MISSING_REQUEST_PROPERTY);
            }
            cfcPath = request.getRealPath("/WEB-INF/cfservices-inf/components/v1_0/api-image.cfc");
        }


        try
        {
            long start = System.currentTimeMillis();

            cfcPath = "/WEB-INF/cfservices-inf/components/v1_0/api-image.cfc";
            String method = "rotateImage";
            String arguments = "";
            // extract properties
            CachedImage cachedImage = (CachedImage)props.get(ImageConfigMBeanImpl.FILE);
            Map<String, Object> methodArgs = new HashMap<String, Object>();
            methodArgs.put("image", cachedImage.getFileBytes());
            methodArgs.put("angle", props.get(ImageConfigMBeanImpl.ANGLE) );
            // execute
            Object cfcResult = coldFusionBridge.invoke(cfcPath, method, arguments, methodArgs, request);

            long end = System.currentTimeMillis();


            // Could be a HashMap or a MultiValueMap
            Map payload = (Map) message.getPayload();
            payload.clear();
            payload.put(ImageConfigMBeanImpl.RESULT, cfcResult);//.getImageBytes(FileHelper.fileName(file).split("\\.")[1]) );


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




    public Object imageResizeHandler(Message<?> message) throws ColdFusionException
    {
        Map props = (Map)message.getPayload();
        HttpServletRequest request = (HttpServletRequest)props.get(ApiServerConstants.HTTP_REQUEST);

        try
        {
            long start = System.currentTimeMillis();

            cfcPath = "/WEB-INF/cfservices-inf/components/v1_0/api-image.cfc";
            String method = "resizeImage";
            String arguments = "";
            // extract properties
            CachedImage cachedImage = (CachedImage)props.get(ImageConfigMBeanImpl.FILE);

            Map<String, Object> methodArgs = new HashMap<String, Object>();
            methodArgs.put("image", cachedImage.getFileBytes());
            methodArgs.put(ImageConfigMBeanImpl.WIDTH, props.get(ImageConfigMBeanImpl.WIDTH));
            methodArgs.put(ImageConfigMBeanImpl.HEIGHT, props.get(ImageConfigMBeanImpl.HEIGHT));
            methodArgs.put(ImageConfigMBeanImpl.INTERPOLATION, props.get(ImageConfigMBeanImpl.INTERPOLATION));
            methodArgs.put(ImageConfigMBeanImpl.SCALE_TO_FIT, props.get(ImageConfigMBeanImpl.SCALE_TO_FIT));

            Object cfcResult = coldFusionBridge.invoke(cfcPath, method, arguments, methodArgs, request);

            long end = System.currentTimeMillis();


            // Could be a HashMap or a MultiValueMap
            Map payload = (Map) message.getPayload();
            payload.clear();
            payload.put(ImageConfigMBeanImpl.RESULT, cfcResult);//.getImageBytes( FileHelper.fileName(file).split("\\.")[1] ) );


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
