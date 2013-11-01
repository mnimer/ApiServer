package apiserver.apis.v1_0.images.controllers.manipulations;

import apiserver.apis.v1_0.documents.model.Document;
import apiserver.apis.v1_0.images.gateways.images.ImageResizeGateway;
import apiserver.apis.v1_0.images.gateways.images.ImageRotateGateway;
import apiserver.apis.v1_0.images.gateways.jobs.images.FileResizeJob;
import apiserver.apis.v1_0.images.gateways.jobs.images.FileRotateJob;
import apiserver.core.common.ResponseEntityHelper;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


//TODO: Add ImageNegative
//TODO: Add ImageOverlay
//TODO: Add ImageFlip
//TODO: Add ImageSharpen
//TODO: Add ImageShear, ImageShearDrawingAxis
//TODO: Add ImageRotateDrawingAxis
//TODO: Add Image Drawing Support (array of actions; ImageDrawLine, ImageDrawOval, ImageDrawPoint, ImageDrawQuadraticCurve

/**
 * User: mnimer
 * Date: 9/15/12
 */
@Controller
@Api(value = "/image/manipulation", description = "[IMAGE]")
@RequestMapping("/image/manipulate")
public class ImageManipulationController
{
    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    private ImageResizeGateway imageResizeGateway;

    @Autowired
    private ImageRotateGateway imageRotateGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;




    /**
     * rotate an image
     *
     * @param documentId
     * @param angle
     * @return
     */
    @ApiOperation(value="Rotate an uploaded image")
    @RequestMapping(value = "/{documentId}/rotate", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<byte[]> rotateImageByImage(
            @ApiParam(name = "documentId", required = true, defaultValue = "8D981024-A297-4169-8603-E503CC38EEDA") @PathVariable(value = "documentId") String documentId
            , @ApiParam(name="angle", required = true, defaultValue = "90") @RequestParam(required = true, defaultValue = "90") Integer angle
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException, InterruptedException, ExecutionException, TimeoutException
    {
        FileRotateJob job = new FileRotateJob();
        job.setDocumentId(documentId);
        job.setAngle(angle);

        Future<Map> imageFuture = imageRotateGateway.rotateImage(job);
        FileRotateJob payload = (FileRotateJob)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);


        BufferedImage bufferedImage = payload.getBufferedImage();
        String contentType = payload.getDocument().getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage( bufferedImage, contentType, returnAsBase64 );
        return result;
    }



    /**
     * rotate an image
     *
     * @param file
     * @param angle
     * @return
     */
    @ApiOperation(value="Rotate an uploaded image")
    @RequestMapping(value = "/rotate", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<byte[]> rotateImageByImage(
            @ApiParam(name="file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name="angle", required = true, defaultValue = "90") @RequestParam(required = true, defaultValue = "90") Integer angle
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException, InterruptedException, ExecutionException, TimeoutException
    {
        FileRotateJob job = new FileRotateJob();
        job.setDocumentId(null);
        job.setDocument( new Document(file) );
        job.getDocument().setContentType(file.getContentType() );
        job.getDocument().setFileName(file.getOriginalFilename());
        job.setAngle(angle);

        Future<Map> imageFuture = imageRotateGateway.rotateImage(job);
        FileRotateJob payload = (FileRotateJob)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);


        BufferedImage bufferedImage = payload.getBufferedImage();
        String contentType = payload.getDocument().getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage( bufferedImage, contentType, returnAsBase64 );
        return result;
    }








    /**
     * Resize an image
     *
     * @param documentId
     * @param interpolation - highestQuality,highQuality,mediumQuality,highestPerformance,highPerformance,mediumPerformance,nearest,bilinear,bicubic,bessel,blackman,hamming,hanning,hermite,lanczos,mitchell,quadratic
     * @param width
     * @param height
     * @return
     */
    @ApiOperation(value="Resize an uploaded image")
    @RequestMapping(value = "/{documentId}/resize", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<byte[]> resizeImageByImage(
            @ApiParam(name = "documentId", required = true, defaultValue = "8D981024-A297-4169-8603-E503CC38EEDA") @PathVariable(value = "documentId") String documentId
            , @ApiParam(name="width", required = true, defaultValue = "200") @RequestParam(required = true) Integer width
            , @ApiParam(name="height", required = true, defaultValue = "200") @RequestParam(required = true) Integer height
            , @ApiParam(name="interpolation", required = false, defaultValue = "bicubic") @RequestParam(required = false, defaultValue = "bicubic") String interpolation
            , @ApiParam(name="scaleToFit", required = false, defaultValue = "false") @RequestParam(required = false, defaultValue = "false") Boolean scaleToFit
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException, InterruptedException, ExecutionException, TimeoutException
    {
        FileResizeJob job = new FileResizeJob();
        job.setDocumentId(documentId);
        job.setWidth(width);
        job.setHeight(height);
        job.setInterpolation(interpolation.toUpperCase());
        job.setScaleToFit(scaleToFit);

        Future<Map> imageFuture = imageResizeGateway.resizeImage(job);
        FileResizeJob payload = (FileResizeJob)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);


        BufferedImage bufferedImage = payload.getBufferedImage();
        String contentType = payload.getDocument().getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage( bufferedImage, contentType, returnAsBase64 );
        return result;
    }



    /**
     * Resize an image
     *
     * @param file
     * @param interpolation - highestQuality,highQuality,mediumQuality,highestPerformance,highPerformance,mediumPerformance,nearest,bilinear,bicubic,bessel,blackman,hamming,hanning,hermite,lanczos,mitchell,quadratic
     * @param width
     * @param height
     * @return
     */
    @ApiOperation(value="Resize an uploaded image")
    @RequestMapping(value = "/resize", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<byte[]> resizeImageByImage(
            @ApiParam(name="file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name="width", required = true, defaultValue = "200") @RequestParam(required = true) Integer width
            , @ApiParam(name="height", required = true, defaultValue = "200") @RequestParam(required = true) Integer height
            , @ApiParam(name="interpolation", required = false, defaultValue = "bicubic") @RequestParam(required = false, defaultValue = "bicubic") String interpolation
            , @ApiParam(name="scaleToFit", required = false, defaultValue = "false") @RequestParam(required = false, defaultValue = "false") Boolean scaleToFit
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException, InterruptedException, ExecutionException, TimeoutException
    {
        FileResizeJob job = new FileResizeJob();
        job.setDocumentId(null);
        job.setDocument( new Document(file) );
        job.getDocument().setContentType( file.getContentType() );
        job.getDocument().setFileName(file.getOriginalFilename());
        job.setWidth(width);
        job.setHeight(height);
        job.setInterpolation(interpolation.toUpperCase());
        job.setScaleToFit(scaleToFit);

        Future<Map> imageFuture = imageResizeGateway.resizeImage(job);
        FileResizeJob payload = (FileResizeJob)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);


        BufferedImage bufferedImage = payload.getBufferedImage();
        String contentType = payload.getDocument().getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage( bufferedImage, contentType, returnAsBase64 );
        return result;
    }


}
