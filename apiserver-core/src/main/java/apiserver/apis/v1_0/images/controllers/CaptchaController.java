package apiserver.apis.v1_0.images.controllers;

import apiserver.apis.v1_0.common.HttpChannelInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/15/12
 */
@Controller
@RequestMapping("/image/captcha")
public class CaptchaController
{
    @Autowired
    public HttpChannelInvoker channelInvoker;

    @Autowired
    public MessageChannel captchaInputChannel;



    /**
     * generate a captcha
     *
     * @param text
     * @param difficulty
     * @param width
     * @param height
     * @return
     */
    @RequestMapping("/generate")
    public ModelAndView getCaptcha(
            HttpServletRequest request
            , HttpServletResponse response
            , @RequestParam(required = true) String text
            , @RequestParam(required = false, defaultValue = "medium") String difficulty
            , @RequestParam(required = true) Integer width
            , @RequestParam(required = true) Integer height
            , @RequestParam(required = false, defaultValue = "40") int fontSize
            , @RequestParam(required = false, defaultValue = "Verdana,Arial,Courier New,Courier") int fontFamily )
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("text", text);
        args.put("difficulty", difficulty);
        args.put("width", width);
        args.put("height", height);
        args.put("fontSize", fontSize);
        args.put("fontFamily", fontFamily);

        return channelInvoker.invokeGenericChannel(request, response, null, captchaInputChannel);
    }

}
