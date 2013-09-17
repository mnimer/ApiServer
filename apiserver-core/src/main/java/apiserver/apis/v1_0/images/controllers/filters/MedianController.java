package apiserver.apis.v1_0.images.controllers.filters;

import apiserver.apis.v1_0.common.ResponseEntityHelper;
import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterMedianGateway;
import apiserver.apis.v1_0.images.models.ImageModel;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping("/image-filters")
public class MedianController
{
    public final Logger log = LoggerFactory.getLogger(MedianController.class);

    @Autowired
    private ApiImageFilterMedianGateway imageFilterMedianGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;



    /**
     * This filter replaces each pixel by the median of the input pixel and its eight neighbours. Each of the RGB channels is considered separately.
     *
     * @param cacheId
     * @param returnAsBase64
     * @return image
     */
    @ApiOperation(value = "This filter replaces each pixel by the median of the input pixel and its eight neighbours. Each of the RGB channels is considered separately.")
    @RequestMapping(value = "/{cacheId}/median", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> imageMedianById(
            @ApiParam(name = "cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52")  @PathVariable("cacheId") String cacheId
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        ImageModel args = new ImageModel();
        args.setCacheId(cacheId);

        Future<Map> imageFuture = imageFilterMedianGateway.imageMedianFilter(args);
        ImageModel payload = (ImageModel)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getProcessedFile();
        String contentType = payload.getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, returnAsBase64);
        return result;
    }



    /**
     * This filter replaces each pixel by the median of the input pixel and its eight neighbours. Each of the RGB channels is considered separately.
     *
     * @param file
     * @param returnAsBase64
     * @return image
     */
    @ApiOperation(value = "This filter replaces each pixel by the median of the input pixel and its eight neighbours. Each of the RGB channels is considered separately.")
    @RequestMapping(value = "/median", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<byte[]> imageMedianByFile(
            @ApiParam(name = "file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        ImageModel args = new ImageModel();
        args.setFile(file);

        Future<Map> imageFuture = imageFilterMedianGateway.imageMedianFilter(args);
        ImageModel payload = (ImageModel)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getProcessedFile();
        String contentType = payload.getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage( bufferedImage, contentType, returnAsBase64 );
        return result;
    }



}
