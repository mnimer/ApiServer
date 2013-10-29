package apiserver.apis.v1_0.images.controllers.filters;

import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterGrayScaleGateway;
import apiserver.apis.v1_0.images.gateways.jobs.ImageDocumentJob;
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
public class GrayscaleController
{
    public final Logger log = LoggerFactory.getLogger(GrayscaleController.class);

    @Autowired
    private ApiImageFilterGrayScaleGateway imageFilterGrayScaleGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;


    /**
     * This filter converts an uploaded image to a grayscale image. To do this it finds the brightness of each pixel and sets the red, green and blue of the output to the brightness value. But what is the brightness? The simplest answer might be that it is the average of the RGB components, but that neglects the way in which the human eye works. The eye is much more sensitive to green and red than it is to blue, and so we need to take less acount of the blue and more account of the green. The weighting used by GrayscaleFilter is: luma = 77R + 151G + 28B
     *
     * @param documentId
     * @return
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    @ApiOperation(value = "This filter converts an image to a grayscale image. To do this it finds the brightness of each pixel and sets the red, green and blue of the output to the brightness value. But what is the brightness? The simplest answer might be that it is the average of the RGB components, but that neglects the way in which the human eye works. The eye is much more sensitive to green and red than it is to blue, and so we need to take less acount of the blue and more account of the green. The weighting used by GrayscaleFilter is: luma = 77R + 151G + 28B")
    @RequestMapping(value = "/{documentId}/grayscale", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> imageGrayscaleByFile(
            @ApiParam(name = "documentId", required = true, defaultValue = "8D981024-A297-4169-8603-E503CC38EEDA") @PathVariable(value = "documentId") String documentId
    ) throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        ImageDocumentJob args = new ImageDocumentJob();
        args.setDocumentId(documentId);

        Future<Map> imageFuture = imageFilterGrayScaleGateway.imageGrayScaleFilter(args);
        ImageDocumentJob payload = (ImageDocumentJob)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getBufferedImage();
        String contentType = payload.getDocument().getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage( bufferedImage, contentType, false );
        return result;
    }

}
