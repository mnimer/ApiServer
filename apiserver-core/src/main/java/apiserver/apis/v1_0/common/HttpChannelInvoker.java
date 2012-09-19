package apiserver.apis.v1_0.common;

import apiserver.ApiServerConstants;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.message.GenericMessage;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/18/12
 */
public class HttpChannelInvoker
{
    public ModelAndView invokeGenericChannel(HttpServletRequest request, HttpServletResponse response, Map arguments, MessageChannel channel)
    {
        if( arguments == null )
        {
            arguments = new HashMap<String, Object>();
        }
        arguments.put(ApiServerConstants.HTTP_REQUEST, request);
        arguments.put(ApiServerConstants.HTTP_RESPONSE, response);
        arguments.putAll(arguments);

        GenericMessage msg = new GenericMessage( arguments );
        channel.send(msg);

        Map payload = (Map)msg.getPayload();
        payload.remove(ApiServerConstants.HTTP_REQUEST);
        payload.remove(ApiServerConstants.HTTP_RESPONSE);

        ModelAndView view = new ModelAndView("help");
        view.addAllObjects( payload );
        return view;
    }

}
