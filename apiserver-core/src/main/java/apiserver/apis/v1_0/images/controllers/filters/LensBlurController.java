package apiserver.apis.v1_0.images.controllers.filters;

import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterLensBlurGateway;
import apiserver.apis.v1_0.images.models.filters.LensBlurModel;
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
@RequestMapping("/image/filters")
public class LensBlurController
{
    public final Logger log = LoggerFactory.getLogger(LensBlurController.class);

    @Autowired
    private ApiImageFilterLensBlurGateway imageFilterLensBlurGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;



    /**
     * This filter simulates the blurring caused by a camera lens. You can change the aperture size and shape and also specify blooming of the uploaded image. This filter is very slow.
     *
     * @param file
     * @param radius
     * @param sides
     * @param bloom
     * @param returnAsBase64
     * @return
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    @ApiOperation(value = "This filter simulates the blurring caused by a camera lens. You can change the aperture size and shape and also specify blooming of the image. This filter is very slow.")
    @RequestMapping(value = "/lensblur", method = {RequestMethod.POST})
    public ResponseEntity<byte[]> imageLensBlurByFile(
            @ApiParam(name = "file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name = "radius", required = false, defaultValue = "10") @RequestParam(value = "radius", required = false, defaultValue = "10") float radius
            , @ApiParam(name = "sides", required = false, defaultValue = "5") @RequestParam(value = "sides", required = false, defaultValue = "5") int sides
            , @ApiParam(name = "bloom", required = false, defaultValue = "2") @RequestParam(value = "bloom", required = false, defaultValue = "2") float bloom
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        LensBlurModel args = new LensBlurModel();
        args.setFile(file);
        args.setRadius(radius);
        args.setSides(sides);
        args.setBloom(bloom);

        Future<Map> imageFuture = imageFilterLensBlurGateway.imageLensBlurFilter(args);
        FileModel payload = (FileModel)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getBufferedImage();
        String contentType = payload.getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage( bufferedImage, contentType, returnAsBase64 );
        return result;
    }


}
