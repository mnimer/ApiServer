package apiserver.apis.v1_0.images.controllers.filters;

import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterGlowGateway;
import apiserver.apis.v1_0.images.gateways.jobs.ImageDocumentJob;
import apiserver.apis.v1_0.images.gateways.jobs.filters.GlowJob;
import apiserver.core.common.ResponseEntityHelper;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
public class GlowController
{
    public final Logger log = LoggerFactory.getLogger(GlowController.class);

    @Autowired
    private ApiImageFilterGlowGateway imageFilterGlowGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;


    /**
     * This filter produces a glowing effect on an uploaded image by adding a blurred version of the image to subtracted from the original image.
     *
     * @param documentId
     * @param amount
     * @return
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    @ApiOperation(value = "This filter produces a glowing effect on an image by adding a blurred version of the image to subtracted from the original image.")
    @RequestMapping(value = "/{documentId}/glow", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> imageGlowByFile(
            @ApiParam(name = "documentId", required = true, defaultValue = "8D981024-A297-4169-8603-E503CC38EEDA") @PathVariable(value = "documentId") String documentId
            , @ApiParam(name = "amount", required = true, defaultValue = "2") @RequestParam(required = false, defaultValue = "2") int amount
    ) throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        GlowJob args = new GlowJob();
        args.setDocumentId(documentId);
        args.setAmount(amount);

        Future<Map> imageFuture = imageFilterGlowGateway.imageGlowFilter(args);
        ImageDocumentJob payload = (ImageDocumentJob)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getBufferedImage();
        String contentType = payload.getDocument().getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage( bufferedImage, contentType, false );
        return result;
    }


}
