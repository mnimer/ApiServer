package apiserver.apis.v1_0.images.controllers.filters;

import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterMaskGateway;
import apiserver.apis.v1_0.images.gateways.jobs.filters.MaskJob;
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
public class MaskController
{
    public final Logger log = LoggerFactory.getLogger(MaskController.class);

    @Autowired
    private ApiImageFilterMaskGateway imageFilterMaskGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;


    /**
     * This filter blurs an uploaded image very slightly using a 3x3 blur kernel.
     *
     * @param documentId
     * @param maskFile
     * @return
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    @ApiOperation(value = "This filter blurs an image very slightly using a 3x3 blur kernel.")
    @RequestMapping(value = "/{documentId}/mask", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<byte[]> imageMaskByFile(
            @ApiParam(name = "documentId", required = true, defaultValue = "8D981024-A297-4169-8603-E503CC38EEDA") @PathVariable(value = "documentId") String documentId
            , @ApiParam(name = "maskFile", required = true) @RequestParam MultipartFile maskFile
    ) throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        MaskJob args = new MaskJob();
        args.setDocumentId(documentId);
        args.setMask(maskFile);

        Future<Map> imageFuture = imageFilterMaskGateway.imageMaskFilter(args);
        FileModel payload = (FileModel)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getBufferedImage();
        String contentType = payload.getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage( bufferedImage, contentType, false );
        return result;
    }


}
