package apiserver.apis.v1_0.images.controllers.filters;

import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterDespeckleGateway;
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
public class DespeckleController
{
    public final Logger log = LoggerFactory.getLogger(DespeckleController.class);

    @Autowired
    private ApiImageFilterDespeckleGateway imageFilterDespeckleGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;


    /**
     * This filter reduces light noise in an image using the eight hull algorithm described in Applied Optics, Vol. 24, No. 10, 15 May 1985, "Geometric filter for Speckle Reduction", by Thomas R Crimmins. Basically, it tries to move each pixel closer in value to its neighbours. As it only has a small effect, you may need to apply it several times. This is good for removing small levels of noise from an image but does give the image some fuzziness.
     *
     * @param file
     * @param returnAsBase64
     * @return
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    @ApiOperation(value = "This filter reduces light noise in an image using the eight hull algorithm described in Applied Optics, Vol. 24, No. 10, 15 May 1985, \"Geometric filter for Speckle Reduction\", by Thomas R Crimmins. Basically, it tries to move each pixel closer in value to its neighbours. As it only has a small effect, you may need to apply it several times. This is good for removing small levels of noise from an image but does give the image some fuzziness.")
    @RequestMapping(value = "/despeckle", method = {RequestMethod.POST})
    public ResponseEntity<byte[]> imageDespeckleByFile(
            @ApiParam(name = "file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        FileModel args = new FileModel();
        args.setFile(file);

        Future<Map> imageFuture = imageFilterDespeckleGateway.imageDespeckleFilter(args);
        FileModel payload = (FileModel)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getBufferedImage();
        String contentType = payload.getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage( bufferedImage, contentType, returnAsBase64 );
        return result;
    }

}
