package apiserver.apis.v1_0.images.service.coldfusion;

import apiserver.ApiServerConstants;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
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
public class CaptchaCFService
{


    public IColdFusionBridge coldFusionBridge;
    public void setColdFusionBridge(IColdFusionBridge coldFusionBridge)
    {
        this.coldFusionBridge = coldFusionBridge;
    }

    public Object generateCaptchaHandler(Message<?> message) throws ColdFusionException, MessageConfigException
    {
        Map props = (Map)message.getPayload();
        HttpServletRequest request = (HttpServletRequest)props.get(ApiServerConstants.HTTP_REQUEST);

        try
        {
            long start = System.currentTimeMillis();
            String cfcPath = "/WEB-INF/cfservices-inf/components/v1_0/api-captcha.cfc";
            String method = "generateCaptcha";
            Object[] methodArgs = extractProperties(props);


            Map cfcResult = (Map)coldFusionBridge.invoke(cfcPath, method, methodArgs, request);
            long end = System.currentTimeMillis();

            // Could be a HashMap or a MultiValueMap
            Map payload = (Map) message.getPayload();
            if( cfcResult instanceof  Map )
            {
                payload.putAll( cfcResult );
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


    private Object[] extractProperties(Map props)
    {
        props.remove(ApiServerConstants.HTTP_REQUEST);
        props.remove(ApiServerConstants.HTTP_RESPONSE);

        Object[] methodArgs = {};
        if( props.size() > 0 )
        {
            methodArgs = new Object[props.size()];
        }

        int indx = 0;
        for (Object key : props.keySet())
        {
            methodArgs[indx++] = props.get(key);
        }
        props.clear();
        return methodArgs;
    }
}
