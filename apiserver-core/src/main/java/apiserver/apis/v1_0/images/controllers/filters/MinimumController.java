package apiserver.apis.v1_0.images.controllers.filters;

import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterMinimumGateway;
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
import org.springframework.web.bind.annotation.ResponseBody;

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
public class MinimumController
{
    public final Logger log = LoggerFactory.getLogger(MinimumController.class);

    @Autowired
    private ApiImageFilterMinimumGateway imageFilterMinimumGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;


    /**
     * This filter replaces each pixel by the median of the input pixel and its eight neighbours. Each of the RGB channels is considered separately.
     *
     * @param documentId
     * @return
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    @ApiOperation(value = "This filter replaces each pixel by the minimum of the input pixel and its eight neighbours. Each of the RGB channels is considered separately.")
    @RequestMapping(value = "/{documentId}/minimum", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<byte[]> imageMinimumByFile(
            @ApiParam(name = "documentId", required = true, defaultValue = "8D981024-A297-4169-8603-E503CC38EEDA") @PathVariable(value = "documentId") String documentId
    ) throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        ImageDocumentJob args = new ImageDocumentJob();
        args.setDocumentId(documentId);

        Future<Map> imageFuture = imageFilterMinimumGateway.imageMinimumFilter(args);
        ImageDocumentJob payload = (ImageDocumentJob)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getBufferedImage();
        String contentType = payload.getDocument().getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage( bufferedImage, contentType, false );
        return result;
    }


}
