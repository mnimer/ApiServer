package apiserver.apis.v1_0.status.controllers;

import apiserver.apis.v1_0.common.HttpChannelInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.integration.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Autowired(required = false)
    private HttpServletRequest request;


    public MessageChannel healthInputChannel;
    public MessageChannel coldFusionInputChannel;


    @Autowired
    public HttpChannelInvoker httpChannelInvoker;

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




    @RequestMapping(value="/health", method = RequestMethod.GET)
    public ModelAndView systemCheck()
    {
        return httpChannelInvoker.invokeGenericChannel(request, null, null, healthInputChannel);
    }





    @RequestMapping(value="/coldfusion/health", method = RequestMethod.GET)
    public ModelAndView coldFusionCheck() throws ServletException, IOException
    {
        return httpChannelInvoker.invokeGenericChannel(request, null, null, coldFusionInputChannel);
    }
}
