package api.status;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.ws.RequestWrapper;
import java.util.Date;

/**
 * User: mnimer
 * Date: 8/17/12
 */
@Controller
@RequestMapping("/v1-0")
public class StatusController
{

    @RequestMapping("/ping")
    public ModelAndView systemCheck()
    {
        ModelAndView view = new ModelAndView("help");
        view.addObject("status", "ok");
        view.addObject("timestamp", new Date().toGMTString());
        return view;
    }


    @RequestMapping("/coldfusion/ping")
    public ModelAndView coldFusionCheck()
    {
        throw new RuntimeException("Not Implemented Yet");
        /**
         * todo Pull the service from the service.coldfusion Bundle and invoke the ping() method
         */
    }
}
