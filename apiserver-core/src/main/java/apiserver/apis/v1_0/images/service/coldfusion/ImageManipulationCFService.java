package apiserver.apis.v1_0.images.service.coldfusion;

import apiserver.ApiServerConstants;
import apiserver.apis.v1_0.images.FileHelper;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.exceptions.ColdFusionException;
import apiserver.exceptions.MessageConfigException;
import coldfusion.cfc.CFCProxy;
import coldfusion.image.Image;
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
            CFCProxy myCFC = new CFCProxy(cfcPath, false);
            Object file = props.get(ImageConfigMBeanImpl.FILE);
            Object[] myArgs = {FileHelper.fileBytes(file), props.get(ImageConfigMBeanImpl.ANGLE)};
            Image cfcResult = (Image)myCFC.invoke("rotateImage", myArgs);
            long end = System.currentTimeMillis();

            // Could be a HashMap or a MultiValueMap
            Map payload = (Map) message.getPayload();
            payload.clear();
            payload.put("image", cfcResult.getImageBytes(FileHelper.fileName(file).split("\\.")[1]) );



            Map cfData = new HashMap();
            cfData.put("executiontime", end - start);
            payload.put("coldfusion", cfData);


            return payload;
        }
        catch (Throwable e)
        {
            //URL location = coldfusion.runtime.NeoPageContext.class.getProtectionDomain().getCodeSource().getLocation();
            //System.out.print(location);

            e.printStackTrace(); //todo use logging library
            throw new RuntimeException(e);
        }
    }


    public Object imageResizeHandler(Message<?> message) throws ColdFusionException
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
            Object file = props.get(ImageConfigMBeanImpl.FILE);
            CFCProxy myCFC = new CFCProxy(cfcPath, false);
            Object[] myArgs = {FileHelper.fileBytes(file)
                    , props.get(ImageConfigMBeanImpl.WIDTH)
                    , props.get(ImageConfigMBeanImpl.HEIGHT)
                    , props.get(ImageConfigMBeanImpl.INTERPOLATION)
                    , props.get(ImageConfigMBeanImpl.SCALE_TO_FIT) };
            Image cfcResult = (Image)myCFC.invoke("resizeImage", myArgs);
            long end = System.currentTimeMillis();

            // Could be a HashMap or a MultiValueMap
            Map payload = (Map) message.getPayload();
            payload.clear();
            payload.put("image", cfcResult.getImageBytes( FileHelper.fileName(file).split("\\.")[1] ) );


            Map cfData = new HashMap();
            cfData.put("executiontime", end - start);
            payload.put("coldfusion", cfData);


            return payload;
        }
        catch (Throwable e)
        {
            //URL location = coldfusion.runtime.NeoPageContext.class.getProtectionDomain().getCodeSource().getLocation();
            //System.out.print(location);

            e.printStackTrace(); //todo use logging library
            throw new RuntimeException(e);
        }
    }
}
