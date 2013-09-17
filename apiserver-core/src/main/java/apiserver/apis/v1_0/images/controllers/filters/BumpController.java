package apiserver.apis.v1_0.images.controllers.filters;

import apiserver.apis.v1_0.common.ResponseEntityHelper;
import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterBumpGateway;
import apiserver.apis.v1_0.images.models.ImageModel;
import apiserver.apis.v1_0.images.models.filters.BumpModel;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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

public class BumpController
{
    @Autowired
    private ApiImageFilterBumpGateway imageFilterBumpGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;


    /**
     * This filter does a simple convolution which emphasises edges in an cached image.
     *
     * @param cacheId
     * @param edgeAction
     * @param useAlpha
     * @param matrix
     * @param returnAsBase64
     * @return
     * @throws java.util.concurrent.TimeoutException
     * @throws java.util.concurrent.ExecutionException
     * @throws InterruptedException
     * @throws java.io.IOException
     */
    @ApiOperation(value = "This filter does a simple convolution which emphasises edges in an image.")
    @RequestMapping(value = "/{cacheId}/bump", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> imageBumpById(
            @ApiParam(name = "cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52") @PathVariable("cacheId") String cacheId
            , @ApiParam(name = "edgeAction", required = false, defaultValue = "1") @RequestParam(value = "edgeAction", defaultValue = "1") int edgeAction
            , @ApiParam(name = "useAlpha", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "useAlpha", defaultValue = "true") Boolean useAlpha
            , @ApiParam(name = "matrix", required = false, defaultValue = "-1.0,-1.0,0.0,-1.0,1.0,1.0,0.0,1.0,1.0") @RequestParam(value = "matrix", defaultValue = "-1.0,-1.0,0.0,-1.0,1.0,1.0,0.0,1.0,1.0") String matrix
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws TimeoutException, ExecutionException, InterruptedException, IOException
    {

        // convert string array into float array
        String[] matrixStrings = matrix.split(",");
        float[] matrixValues = new float[matrixStrings.length];
        for (int i = 0; i < matrixStrings.length; i++)
        {
            String s = matrixStrings[i];
            matrixValues[i] = Float.parseFloat(s);
        }


        BumpModel args = new BumpModel();
        args.setCacheId(cacheId);
        args.setEdgeAction(edgeAction);
        args.setUseAlpha(useAlpha);
        args.setMatrix(matrixValues);

        Future<Map> imageFuture = imageFilterBumpGateway.imageBumpFilter(args);
        ImageModel payload = (ImageModel) imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getProcessedFile();
        String contentType = payload.getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, returnAsBase64);
        return result;
    }


    /**
     * This filter does a simple convolution which emphasises edges in an uploaded image.
     *
     * @param file
     * @param edgeAction
     * @param useAlpha
     * @param matrix
     * @param returnAsBase64
     * @return
     * @throws java.util.concurrent.TimeoutException
     * @throws java.util.concurrent.ExecutionException
     * @throws InterruptedException
     * @throws java.io.IOException
     */
    @ApiOperation(value = "This filter does a simple convolution which emphasises edges in an image.")
    @RequestMapping(value = "/bump", method = {RequestMethod.POST})
    public ResponseEntity<byte[]> imageBumpByFile(
            @ApiParam(name = "file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name = "edgeAction", required = false, defaultValue = "1") @RequestParam(value = "edgeAction", required = false, defaultValue = "1") int edgeAction
            , @ApiParam(name = "useAlpha", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "useAlpha", required = false, defaultValue = "true") boolean useAlpha
            , @ApiParam(name = "matrix", required = false, defaultValue = "-1.0,-1.0,0.0,-1.0,1.0,1.0,0.0,1.0,1.0") @RequestParam(value = "matrix", required = false, defaultValue = "-1.0,-1.0,0.0,-1.0,1.0,1.0,0.0,1.0,1.0") String matrix
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        // convert string array into float array
        String[] matrixStrings = matrix.split(",");
        float[] matrixValues = new float[matrixStrings.length];
        for (int i = 0; i < matrixStrings.length; i++)
        {
            String s = matrixStrings[i];
            matrixValues[i] = Float.parseFloat(s);
        }

        BumpModel args = new BumpModel();
        args.setFile(file);
        args.setEdgeAction(edgeAction);
        args.setUseAlpha(useAlpha);
        args.setMatrix(matrixValues);


        Future<Map> imageFuture = imageFilterBumpGateway.imageBumpFilter(args);
        ImageModel payload = (ImageModel) imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getProcessedFile();
        String contentType = payload.getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, returnAsBase64);
        return result;
    }
}