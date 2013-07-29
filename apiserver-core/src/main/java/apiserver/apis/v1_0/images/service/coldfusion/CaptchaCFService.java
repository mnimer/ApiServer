package apiserver.apis.v1_0.images.service.coldfusion;

import apiserver.ApiServerConstants;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.core.connectors.coldfusion.IColdFusionBridge;
import apiserver.exceptions.ColdFusionException;
import apiserver.exceptions.MessageConfigException;
import org.springframework.beans.factory.annotation.Autowired;
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
            String arguments = "text&difficulty&width&height&debug:false&fontSize:40&fontFamily:Verdana,Arial,Courier New,Courier";
            Map<String, Object> methodArgs = extractProperties(props);

            Map cfcResult = (Map)coldFusionBridge.invoke(cfcPath, method, arguments, methodArgs);
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


    private  Map<String, Object> extractProperties(Map props)
    {
        props.remove(ApiServerConstants.HTTP_REQUEST);
        props.remove(ApiServerConstants.HTTP_RESPONSE);

        Map<String, Object> methodArgs = new HashMap<String, Object>();
        methodArgs.putAll(props);

        props.clear();
        return methodArgs;
    }
}
