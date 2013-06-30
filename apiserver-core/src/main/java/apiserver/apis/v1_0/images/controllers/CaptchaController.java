package apiserver.apis.v1_0.images.controllers;

import apiserver.apis.v1_0.common.HttpChannelInvoker;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
    @ApiOperation(value = "Get a new image to use for captcha checks")
    @RequestMapping(value = "/generate", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<byte[]> getCaptcha(
            @ApiParam(name="text", required = true, defaultValue = "hello world") @RequestParam(required = true) String text
            , @ApiParam(name="width", required = true, defaultValue = "400") @RequestParam(required = true) Integer width
            , @ApiParam(name="height", required = true, defaultValue = "200") @RequestParam(required = true) Integer height
            , @ApiParam(name="fontSize", required = true, defaultValue = "20") @RequestParam(required = true) Integer fontSize
            , @ApiParam(name="difficulty", required = false, defaultValue = "medium", allowableValues = "low,medium,high") @RequestParam(required = false, defaultValue = "medium") String difficulty
            , @ApiParam(name="returnAsBase64", required = false, defaultValue = "true", allowableValues="true,false") @RequestParam(required = false, defaultValue = "false") Boolean returnAsBase64
    )  throws IOException
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


        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( ((BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT)), "jpg", returnAsBase64 );
        return result;
    }


}
