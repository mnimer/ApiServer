package apiserver.apis.v1_0.images.service.coldfusion;

import apiserver.ApiServerConstants;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
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
public class CaptchaCFService
{
    private static String cfcPath;

    public Object generateCaptchaHandler(Message<?> message) throws ColdFusionException, MessageConfigException
    {
        Map props = (Map)message.getPayload();
        HttpServletRequest request = (HttpServletRequest)props.get(ApiServerConstants.HTTP_REQUEST);

        if( cfcPath == null )
        {
            if( request == null )
            {
                throw new MessageConfigException(MessageConfigException.MISSING_REQUEST_PROPERTY);
            }
            cfcPath = request.getRealPath("/WEB-INF/cfservices-inf/components/v1_0/api-captcha.cfc");
        }

        try
        {
            props.remove(ApiServerConstants.HTTP_REQUEST);
            props.remove(ApiServerConstants.HTTP_RESPONSE);

            long start = System.currentTimeMillis();
            CFCProxy myCFC = new CFCProxy(cfcPath, false);


            Object[] myArgs = {};
            if( props.size() > 0 )
            {
                myArgs = new Object[props.size()];
            }

            int indx = 0;
            for (Object key : props.keySet())
            {
                myArgs[indx++] = props.get(key);
            }
            props.clear();


            Object cfcResult = myCFC.invoke("generateCaptcha", myArgs);
            long end = System.currentTimeMillis();

            // Could be a HashMap or a MultiValueMap
            Map payload = (Map) message.getPayload();
            if( cfcResult instanceof  Map )
            {
                payload.putAll( (Map)cfcResult );
            }
            else
            {
                payload.put(ImageConfigMBeanImpl.RESULT, cfcResult);
            }


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
