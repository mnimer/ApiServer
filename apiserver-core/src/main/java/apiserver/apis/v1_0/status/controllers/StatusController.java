package apiserver.apis.v1_0.status.controllers;

import apiserver.apis.v1_0.common.HttpChannelInvoker;
import com.wordnik.swagger.core.Api;
import org.apache.jackrabbit.servlet.ServletRepository;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.integration.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@Api("/apidoc")
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
    public ModelAndView coldFusionCheck() throws ServletException, IOException
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


    private GenericServlet getServletInstance(String name, HttpServletRequest req)
    {

        ServletHandler handler = (ServletHandler) req.getServletContext().getServletRegistrations().get(name);

   /* A list of all the servlets that don't implement the class 'servlet',
      (i.e. They should be kept in the context */
        List<ServletHolder> servlets = new ArrayList<ServletHolder>();

   /* The names all the servlets that we remove so we can drop the mappings too */
        Set<String> names = new HashSet<String>();

        for (ServletHolder holder : handler.getServlets())
        {
      /* If it is the class we want to remove, then just keep track of its name */
            if ( holder.getServletInstance().getServletInfo().toString() == name)
            {
                return (GenericServlet)holder.getServletInstance();
            }
        }

        return null;
    }
}
