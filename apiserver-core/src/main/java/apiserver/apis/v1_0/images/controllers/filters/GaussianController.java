package apiserver.apis.v1_0.images.controllers.filters;

import apiserver.apis.v1_0.common.ResponseEntityHelper;
import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterGaussianGateway;
import apiserver.apis.v1_0.images.models.ImageModel;
import apiserver.apis.v1_0.images.models.filters.GaussianModel;
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
public class GaussianController
{
    public final Logger log = LoggerFactory.getLogger(GaussianController.class);

    @Autowired
    private ApiImageFilterGaussianGateway imageFilterGaussianGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;



    /**
     * This filter performs a Gaussian blur on an cached image.
     *
     * @param cacheId
     * @param radius
     * @param returnAsBase64
     * @return
     * @throws java.util.concurrent.TimeoutException
     * @throws java.util.concurrent.ExecutionException
     * @throws InterruptedException
     * @throws java.io.IOException
     */
    @ApiOperation(value = "This filter performs a Gaussian blur on an image.")
    @RequestMapping(value = "/{cacheId}/gaussian", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> imageGaussianById(
            @ApiParam(name = "cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52") @PathVariable("cacheId") String cacheId
            , @ApiParam(name = "radius", required = true, defaultValue = "2") @RequestParam(required = false, defaultValue = "2") int radius
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        GaussianModel args = new GaussianModel();
        args.setCacheId(cacheId);
        args.setRadius(radius);

        Future<Map> imageFuture = imageFilterGaussianGateway.imageGaussianFilter(args);
        ImageModel payload = (ImageModel)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getProcessedFile();
        String contentType = payload.getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, returnAsBase64);
        return result;
    }


    /**
     * This filter performs a Gaussian blur on an uploaded image.
     *
     * @param file
     * @param radius
     * @param returnAsBase64
     * @return
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    @ApiOperation(value = "This filter performs a Gaussian blur on an image.")
    @RequestMapping(value = "/gaussian", method = {RequestMethod.POST})
    public ResponseEntity<byte[]> imageDespeckleByFile(
            @ApiParam(name = "file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name = "radius", required = true, defaultValue = "2") @RequestParam(required = false, defaultValue = "2") int radius
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        GaussianModel args = new GaussianModel();
        args.setFile(file);
        args.setRadius(radius);

        Future<Map> imageFuture = imageFilterGaussianGateway.imageGaussianFilter(args);
        ImageModel payload = (ImageModel)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getProcessedFile();
        String contentType = payload.getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage( bufferedImage, contentType, returnAsBase64 );
        return result;
    }


}
