package apiserver.apis.v1_0.images.controllers;

import apiserver.apis.v1_0.common.HttpChannelInvoker;
import coldfusion.image.Image;
import coldfusion.runtime.Struct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/15/12
 */
@Controller
@RequestMapping("/image-captcha")
public class CaptchaController
{
    @Autowired(required = false)
    private HttpServletRequest request;

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
    @RequestMapping(value = "/generate", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<byte[]> getCaptcha(
            @RequestParam(required = true) String text
            , @RequestParam(required = true) Integer width
            , @RequestParam(required = true) Integer height
            , @RequestParam(required = true) Integer fontSize
            , @RequestParam(required = false, defaultValue = "medium") String difficulty )  throws IOException
    {
        Map args = new LinkedHashMap();
        args.put("text", text);
        args.put("difficulty", difficulty);
        args.put("width", width);
        args.put("height", height);
        args.put("debug", false);
        args.put("fontSize", fontSize);
        //args.put("fontFamily", fontFamily);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, captchaInputChannel);


        //todo: log CF execution time to MBean analytics

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<byte[]>(((Image)view.getModel().get("result")).getImageBytes("jpg"), headers, HttpStatus.CREATED);
    }


}
