package apiserver.apis.v1_0.images.controllers.filters;

import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterBoxBlurGateway;
import apiserver.apis.v1_0.images.gateways.jobs.filters.BoxBlurJob;
import apiserver.core.common.ResponseEntityHelper;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Controller
@RequestMapping("/image/filters")
public class BoxBlurController
{

    @Autowired
    private ApiImageFilterBoxBlurGateway imageFilterBoxBlurGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;


    /**
     * A filter which performs a box blur on an uploaded image. The horizontal and vertical blurs can be specified separately and a number of iterations can be given which allows an approximation to Gaussian blur.
     *
     * @param documentId
     * @param hRadius
     * @param vRadius
     * @param iterations
     * @param preMultiplyAlpha
     * @param returnAsBase64
     * @return
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    @ApiOperation(value = "A filter which performs a box blur on an image. The horizontal and vertical blurs can be specified separately and a number of iterations can be given which allows an approximation to Gaussian blur.  Supported formats are .json, .png, .jpg, .base64")
    @RequestMapping(value = "/{documentId}/boxblur", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<byte[]> imageBoxBlurByFile(
            @ApiParam(name = "documentId", required = true) @PathVariable(value = "documentId") String documentId
            , @ApiParam(name = "hRadius", required = false, defaultValue = "2", value = "the horizontal radius of blur") @RequestParam(value = "hRadius", defaultValue = "2") int hRadius
            , @ApiParam(name = "vRadius", required = false, defaultValue = "2", value = "the vertical radius of blur") @RequestParam(value = "vRadius", defaultValue = "2") int vRadius
            , @ApiParam(name = "iterations", required = false, defaultValue = "1", value = "the number of time to iterate the blur") @RequestParam(value = "iterations", defaultValue = "1") int iterations
            , @ApiParam(name = "preMultiplyAlpha", required = false, defaultValue = "true", allowableValues = "true,false", value = "pre multiply the alpha channel") @RequestParam(value = "preMultiplyAlpha", defaultValue = "true") boolean preMultiplyAlpha
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        BoxBlurJob args = new BoxBlurJob();
        args.setDocumentId(documentId);
        args.setHRadius(hRadius);
        args.setVRadius(vRadius);
        args.setIterations(iterations);
        args.setPreMultiplyAlpha(preMultiplyAlpha);

        Future<Map> imageFuture = imageFilterBoxBlurGateway.imageBoxBlurFilter(args);
        BoxBlurJob payload = (BoxBlurJob) imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getBufferedImage();
        String contentType = payload.getDocument().getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, returnAsBase64);
        return result;
    }
}