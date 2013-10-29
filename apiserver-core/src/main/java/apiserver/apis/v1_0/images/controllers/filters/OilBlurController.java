package apiserver.apis.v1_0.images.controllers.filters;

import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterOilGateway;
import apiserver.apis.v1_0.images.gateways.jobs.filters.OilJob;
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
public class OilBlurController
{
    public final Logger log = LoggerFactory.getLogger(OilBlurController.class);

    @Autowired
    private ApiImageFilterOilGateway imageFilterOilGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;




    /**
     * This filter replaces each pixel by the median of the input pixel and its eight neighbours. Each of the RGB channels is considered separately.
     *
     * @param file
     * @param level
     * @param range
     * @param returnAsBase64
     * @return
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    @ApiOperation(value = "This filter produces an oil painting effect as described in the book \"Beyond Photography - The Digital Darkroom\". You can specify the smearing radius. It's quite a slow filter especially with a large radius.")
    @RequestMapping(value = "/{documentId}/oil", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<byte[]> imageOilBlurByFile(
            @ApiParam(name = "documentId", required = true, defaultValue = "8D981024-A297-4169-8603-E503CC38EEDA") @PathVariable(value = "documentId") String documentId
            , @ApiParam(name="level", required = true, defaultValue = "3")  @RequestParam(value="angle", required = false, defaultValue="3") int level
            , @ApiParam(name="range", required = true, defaultValue = "256")  @RequestParam(value="range", required = false,  defaultValue="256") int range
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        OilJob args = new OilJob();
        args.setDocumentId(documentId);
        args.setLevels(level);
        args.setRange(range);


        Future<Map> imageFuture = imageFilterOilGateway.imageOilFilter(args);
        FileModel payload = (FileModel)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getBufferedImage();
        String contentType = payload.getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage( bufferedImage, contentType, returnAsBase64 );
        return result;
    }



}
