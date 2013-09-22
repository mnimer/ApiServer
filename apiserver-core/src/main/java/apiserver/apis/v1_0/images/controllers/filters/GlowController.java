package apiserver.apis.v1_0.images.controllers.filters;

import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterGlowGateway;
import apiserver.apis.v1_0.images.models.filters.GlowModel;
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
public class GlowController
{
    public final Logger log = LoggerFactory.getLogger(GlowController.class);

    @Autowired
    private ApiImageFilterGlowGateway imageFilterGlowGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;




    /**
     * This filter produces a glowing effect on an uploaded image by adding a blurred version of the image to subtracted from the original image.
     * @param file
     * @param amount
     * @param returnAsBase64
     * @return
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    @ApiOperation(value = "This filter produces a glowing effect on an image by adding a blurred version of the image to subtracted from the original image.")
    @RequestMapping(value = "/glow", method = {RequestMethod.POST})
    public ResponseEntity<byte[]> imageGlowByFile(
            @ApiParam(name = "file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name = "amount", required = true, defaultValue = "2") @RequestParam(required = false, defaultValue = "2") int amount
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        GlowModel args = new GlowModel();
        args.setFile(file);
        args.setAmount(amount);

        Future<Map> imageFuture = imageFilterGlowGateway.imageGlowFilter(args);
        FileModel payload = (FileModel)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getBufferedImage();
        String contentType = payload.getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage( bufferedImage, contentType, returnAsBase64 );
        return result;
    }


}
