package apiserver.apis.v1_0.status.controllers;

import apiserver.apis.v1_0.status.gateways.ApiStatusColdFusionGateway;
import apiserver.apis.v1_0.status.gateways.ApiStatusGateway;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


/**
 * User: mnimer
 * Date: 8/17/12
 */


@Api("/status")
@Controller
@RequestMapping("/status")
public class StatusController
{
    @Autowired
    ApiStatusGateway gateway;
    @Autowired
    ApiStatusColdFusionGateway CFGateway;

    //Autowired
    ServletContext context;


    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public WebAsyncTask<Map> checkApiServerAsync(HttpServletRequest request, HttpServletResponse response)
    {
        Callable<Map> callable = new Callable<Map>()
        {
            @Override
            public Map call() throws Exception
            {
                Future<Map> result = gateway.checkApiServerAsync();
                return result.get(10000, TimeUnit.MILLISECONDS);

            }
        };

        return new WebAsyncTask<Map>(10000, callable);
    }


    @RequestMapping(value = "/coldfusion/health", method = RequestMethod.GET)
    public Callable<Map> checkColdfusionAsync(HttpServletRequest request, HttpServletResponse response) throws  Exception
    {
        Callable<Map> callable = new Callable<Map>()
        {
            @Override
            public Map call() throws Exception
            {
                Future<Map> result = CFGateway.checkColdfusionAsync();
                Map finalResult = result.get(10000, TimeUnit.MILLISECONDS);
                return finalResult;
            }
        };

        return callable;//new WebAsyncTask<Map>(10000, callable);
    }




    @RequestMapping(value = "/jcr/health", method = RequestMethod.GET)
    public ModelAndView jcrCheck(HttpServletRequest req) throws ServletException, IOException
    {
        ModelAndView view = new ModelAndView("help");


        try
        {
            System.out.println("JCR SERVLET STATUS: SUCCESS - ");// + repository.login().getRootNode().getNodes().toString());
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
