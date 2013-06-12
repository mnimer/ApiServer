package apiserver.apis.v1_0.status.controllers;

import apiserver.apis.v1_0.common.HttpChannelInvoker;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.jcr.Repository;
import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
    ServletContext context;

    @Autowired
    Repository repository;


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


    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public ModelAndView systemCheck()
    {
        return httpChannelInvoker.invokeGenericChannel(request, null, null, healthInputChannel);
    }


    @RequestMapping(value = "/coldfusion/health", method = RequestMethod.GET)
    public ModelAndView coldFusionCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        return httpChannelInvoker.invokeGenericChannel(request, null, null, coldFusionInputChannel);
    }


    @RequestMapping(value = "/jcr/health", method = RequestMethod.GET)
    public ModelAndView jcrCheck(HttpServletRequest req) throws ServletException, IOException
    {
        ModelAndView view = new ModelAndView("help");


        try
        {
            System.out.println("JCR SERVLET STATUS: SUCCESS - " + repository.login().getRootNode().getNodes().toString());
        } catch (Exception ex)
        {
            System.out.println("JCR SERVLET STATUS: FAILED");
        }
        /**
         try
         {

         InitialContext ctx = new InitialContext();
         Context env = (Context) ctx.lookup("java:comp/env");
         Repository repo = (Repository) env.lookup("jcr/repository");
         Session session = repo.login(new SimpleCredentials("admin", "admin".toCharArray()));

         out.println("JCR JNDI STATUS: SUCCESS - " +session.getRootNode().toString() );
         }catch(Exception ex){
         out.println("JCR JNDI STATUS: FAILED");
         }
         **/

        return view;
        //return httpChannelInvoker.invokeGenericChannel(request, null, null, coldFusionInputChannel);
    }


}
