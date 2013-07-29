package apiserver.apis.v1_0.images.controllers;

import apiserver.apis.v1_0.common.ResponseEntityHelper;
import apiserver.apis.v1_0.images.gateways.images.GetCaptchaGateway;
import apiserver.apis.v1_0.images.models.cache.CacheGetModel;
import apiserver.apis.v1_0.images.models.images.GetCaptchaModel;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * User: mnimer
 * Date: 9/15/12
 */
//Controller
@RequestMapping("/image-captcha")
public class CaptchaController
{
    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    private GetCaptchaGateway gateway;

    /**
     * generate a captcha
     *
     * @param text
     * @param difficulty
     * @param width
     * @param height
     * @return
     *
     * todo: log CF execution time to MBean analytics
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
    )  throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        GetCaptchaModel args = new GetCaptchaModel();
        args.setText(text);
        args.setDifficulty(difficulty);
        args.setWidth(width);
        args.setHeight(height);
        args.setFontSize(fontSize);
        args.setReturnAsBase64(false);
        //args.put("fontFamily", fontFamily);

        Future<Map> imageFuture = gateway.getCaptcha(args);
        CacheGetModel payload = (CacheGetModel) imageFuture.get(10000, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getProcessedFile();
        String contentType = payload.getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, returnAsBase64);
        return result;
    }


}
