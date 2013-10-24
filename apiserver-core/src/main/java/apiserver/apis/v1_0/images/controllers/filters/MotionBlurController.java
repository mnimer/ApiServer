package apiserver.apis.v1_0.images.controllers.filters;

import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterMotionBlurGateway;
import apiserver.apis.v1_0.images.gateways.jobs.filters.MotionBlurJob;
import apiserver.core.common.ResponseEntityHelper;
import apiserver.core.models.FileModel;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * User: mikenimer
 * Date: 9/16/13
 */
@Controller
@RequestMapping("/image/filters")
public class MotionBlurController
{
    public final Logger log = LoggerFactory.getLogger(MotionBlurController.class);

    @Autowired
    private ApiImageFilterMotionBlurGateway imageFilterMotionBlurGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;


    /**
     * This filter replaces each pixel by the median of the input pixel and its eight neighbours. Each of the RGB channels is considered separately.
     * @param documentId
     * @param angle
     * @param distance
     * @param rotation
     * @param wrapEdges
     * @param zoom
     * @return
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    @ApiOperation(value = "This filter simulates motion blur on an image. You specify a combination of angle/distance for linear motion blur, a rotaiton angle for spin blur or a zoom factor for zoom blur. You can combine these in any proportions you want to get effects like spiral blurs.")
    @RequestMapping(value = "/{documentId}/motionblur", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<byte[]> imageMotionBlurByFile(
            @ApiParam(name = "documentId", required = true) @PathVariable(value = "documentId") String documentId
            , @ApiParam(name="angle", required = true, defaultValue = "0")  @RequestParam(value="angle", required = false, defaultValue="0") float angle
            , @ApiParam(name="distance", required = true, defaultValue = "1")  @RequestParam(value="distance", required = false,  defaultValue="0") float distance
            , @ApiParam(name="rotation", required = true, defaultValue = "0")  @RequestParam(value="rotation", required = false,  defaultValue="0") float rotation
            , @ApiParam(name="wrapEdges", required = true, defaultValue = "false")  @RequestParam(value="wrapEdges", required = false,  defaultValue="false") boolean wrapEdges
            , @ApiParam(name="zoom", required = true, defaultValue = "0")  @RequestParam(value="zoom", required = false,  defaultValue="0") float zoom
    ) throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        MotionBlurJob args = new MotionBlurJob();
        args.setDocumentId(documentId);
        args.setAngle(angle);
        args.setDistance(distance);
        args.setRotation(rotation);
        args.setWrapEdges(wrapEdges);
        args.setZoom(zoom);


        Future<Map> imageFuture = imageFilterMotionBlurGateway.imageMotionBlurFilter(args);
        FileModel payload = (FileModel)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getBufferedImage();
        String contentType = payload.getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage( bufferedImage, contentType, false );
        return result;
    }



}
