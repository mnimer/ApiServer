package apiserver.apis.v1_0.images.controllers.filters;

import apiserver.apis.v1_0.common.ResponseEntityHelper;
import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterOilGateway;
import apiserver.apis.v1_0.images.models.ImageModel;
import apiserver.apis.v1_0.images.models.filters.OilModel;
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
public class OilBlurController
{
    public final Logger log = LoggerFactory.getLogger(OilBlurController.class);

    @Autowired
    private ApiImageFilterOilGateway imageFilterOilGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;



    /**
     * This filter replaces each pixel by the median of the input pixel and its eight neighbours. Each of the RGB channels is considered separately.
     *
     * @param cacheId
     * @param level
     * @param range
     * @param returnAsBase64
     * @return
     * @throws java.util.concurrent.TimeoutException
     * @throws java.util.concurrent.ExecutionException
     * @throws InterruptedException
     * @throws java.io.IOException
     */
    @ApiOperation(value = "This filter produces an oil painting effect as described in the book \"Beyond Photography - The Digital Darkroom\". You can specify the smearing radius. It's quite a slow filter especially with a large radius.")
    @RequestMapping(value = "/{cacheId}/oil", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> imageOilBlurById(
            @ApiParam(name = "cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52")  @PathVariable("cacheId") String cacheId
            , @ApiParam(name="level", required = true, defaultValue = "3")  @RequestParam(value="level", required = false, defaultValue="3") int level
            , @ApiParam(name="range", required = true, defaultValue = "256")  @RequestParam(value="range", required = false,  defaultValue="256") int range
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        OilModel args = new OilModel();
        args.setCacheId(cacheId);
        args.setLevels(level);
        args.setRange(range);

        Future<Map> imageFuture = imageFilterOilGateway.imageOilFilter(args);
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
    @RequestMapping(value = "/oil", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<byte[]> imageOilBlurByFile(
            @ApiParam(name = "file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name="level", required = true, defaultValue = "3")  @RequestParam(value="angle", required = false, defaultValue="3") int level
            , @ApiParam(name="range", required = true, defaultValue = "256")  @RequestParam(value="range", required = false,  defaultValue="256") int range
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        OilModel args = new OilModel();
        args.setFile(file);
        args.setLevels(level);
        args.setRange(range);


        Future<Map> imageFuture = imageFilterOilGateway.imageOilFilter(args);
        ImageModel payload = (ImageModel)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getProcessedFile();
        String contentType = payload.getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage( bufferedImage, contentType, returnAsBase64 );
        return result;
    }



}
