package apiserver.apis.v1_0.status.services;

import apiserver.ApiServerConstants;
import apiserver.exceptions.ColdFusionException;
import apiserver.exceptions.MessageConfigException;
import coldfusion.cfc.CFCProxy;
import coldfusion.runtime.Struct;
import org.springframework.integration.Message;
import org.springframework.jms.MessageEOFException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/16/12
 */
public class ColdFusionStatusService
{
    private static String cfcPath;

    public Object healthHandler(Message<?> message) throws ColdFusionException, MessageConfigException
    {
        Map props = (Map)message.getPayload();
        HttpServletRequest request = (HttpServletRequest)props.get(ApiServerConstants.HTTP_REQUEST);

        if( cfcPath == null )
        {
            if( request == null )
            {
                throw new MessageConfigException(MessageConfigException.MISSING_REQUEST_PROPERTY);
            }
            cfcPath = request.getRealPath("/WEB-INF/cfservices-inf/components/v1_0/api-status.cfc");
        }

        try
        {
            long start = System.currentTimeMillis();
            CFCProxy myCFC = new CFCProxy(cfcPath, false);
            Object[] myArgs = {};
            Struct cfcResult = (Struct)myCFC.invoke("health", myArgs);
            long end = System.currentTimeMillis();

            // Could be a HashMap or a MultiValueMap
            Map payload = (Map) message.getPayload();
            payload.putAll(cfcResult);
            ((Map)payload.get("coldfusion")).put("executionTime", end-start);
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
