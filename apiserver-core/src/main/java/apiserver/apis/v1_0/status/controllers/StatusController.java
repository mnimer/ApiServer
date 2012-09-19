package apiserver.apis.v1_0.status.controllers;

import apiserver.apis.v1_0.common.HttpChannelInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


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
    public HttpChannelInvoker channelInvoker;


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




    @RequestMapping("/health")
    public ModelAndView systemCheck(HttpServletRequest request, HttpServletResponse response)
    {
        return channelInvoker.invokeGenericChannel(request, response, null, healthInputChannel);
    }





    @RequestMapping("/coldfusion/health")
    public ModelAndView coldFusionCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        return channelInvoker.invokeGenericChannel(request, response, null, coldFusionInputChannel);
    }
}
