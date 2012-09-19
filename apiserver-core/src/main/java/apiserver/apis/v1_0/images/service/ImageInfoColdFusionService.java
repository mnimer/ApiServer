package apiserver.apis.v1_0.images.service;

import apiserver.ApiServerConstants;
import apiserver.exceptions.ColdFusionException;
import apiserver.exceptions.MessageConfigException;
import coldfusion.cfc.CFCProxy;
import coldfusion.runtime.Struct;
import org.springframework.integration.Message;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/18/12
 */
public class ImageInfoColdFusionService
{
    private static String cfcPath;


    public Object imageSizeHandler(Message<?> message) throws ColdFusionException
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
            Object[] myArgs = {};
            Struct cfcResult = (Struct)myCFC.invoke("imageInfo", myArgs);
            long end = System.currentTimeMillis();

            // Could be a HashMap or a MultiValueMap
            Map payload = (Map) message.getPayload();
            payload.putAll(cfcResult);


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



    public Object imageMetadataHandler(Message<?> message) throws ColdFusionException
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
            Object[] myArgs = {};
            Struct cfcResult = (Struct)myCFC.invoke("imageMetadata", myArgs);
            long end = System.currentTimeMillis();

            // Could be a HashMap or a MultiValueMap
            Map payload = (Map) message.getPayload();
            payload.putAll(cfcResult);


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
