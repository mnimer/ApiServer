package apiserver.apis.v1_0.status;

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

    public MessageChannel pingInputChannel;

    @Autowired
    public void setPingInputChannel(MessageChannel pingInputChannel)
    {
        this.pingInputChannel = pingInputChannel;
    }


    @RequestMapping("/ping")
    public ModelAndView systemCheck(HttpServletRequest request, HttpServletResponse response)
    {
        Map<String, Object> arguments = new HashMap<String, Object>();
        GenericMessage msg = new GenericMessage( arguments );
        pingInputChannel.send(msg);


        ModelAndView view = new ModelAndView("help");
        view.addAllObjects( (Map)msg.getPayload() );
        return view;
    }



    @RequestMapping("/coldfusion/ping")
    public ModelAndView coldFusionCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        URL location = coldfusion.runtime.NeoPageContext.class.getProtectionDomain().getCodeSource().getLocation();
        //System.out.print(location);

        Struct cfcResult = null;
        String cfcPath = request.getRealPath("/WEB-INF/cfservices-inf/components/v1_0/status.cfc"); //cache this lookup for performance

        long start = System.currentTimeMillis();
        try
        {
            CFCProxy myCFC = new CFCProxy(cfcPath, false);
            Object[] myArgs = {};
            cfcResult = (Struct)myCFC.invoke("ping", myArgs);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        long end = System.currentTimeMillis();


        ModelAndView view = new ModelAndView("help");
        view.addObject("status", cfcResult.get("status") );
        view.addObject("timestamp", cfcResult.get("data") );
        view.addObject("cflocation", location );
        return view;
    }
}
