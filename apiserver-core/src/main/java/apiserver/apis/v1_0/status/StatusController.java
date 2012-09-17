package apiserver.apis.v1_0.status;

import apiserver.ApiServerConstants;
import coldfusion.cfc.CFCProxy;
import coldfusion.runtime.Struct;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.message.GenericMessage;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * User: mnimer
 * Date: 8/17/12
 */
@Controller
@RequestMapping("/status")
public class StatusController
{

    public MessageChannel healthInputChannel;
    public MessageChannel coldFusionInputChannel;


    @Autowired
    public void setHealthInputChannel(MessageChannel healthInputChannel)
    {
        this.healthInputChannel = healthInputChannel;
    }


    @Autowired
    public void setColdFusionInputChannel(MessageChannel coldFusionInputChannel)
    {
        this.coldFusionInputChannel = coldFusionInputChannel;
    }


    private ModelAndView invokeChannel(HttpServletRequest request, HttpServletResponse response, Map arguments, MessageChannel channel)
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


    @RequestMapping("/health")
    public ModelAndView systemCheck(HttpServletRequest request, HttpServletResponse response)
    {
        return invokeChannel(request, response, null, healthInputChannel);
    }





    @RequestMapping("/coldfusion/health")
    public ModelAndView coldFusionCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        return invokeChannel(request, response, null, coldFusionInputChannel);
    }
}
