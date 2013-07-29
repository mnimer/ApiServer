package apiserver.apis.v1_0.images.controllers;

import apiserver.apis.v1_0.common.HttpChannelInvoker;
import apiserver.apis.v1_0.common.ResponseEntityHelper;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.apis.v1_0.images.gateways.images.ImageResizeGateway;
import apiserver.apis.v1_0.images.gateways.images.ImageRotateGateway;
import apiserver.apis.v1_0.images.models.ImageModel;
import apiserver.apis.v1_0.images.models.images.ImageResizeModel;
import apiserver.apis.v1_0.images.models.images.ImageRotateModel;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
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
//Controller
@RequestMapping("/image-info")
public class ImageManipulationController
{
    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    private ImageResizeGateway imageResizeGateway;

    @Autowired
    private ImageRotateGateway imageRotateGateway;
    /**
     * rotate an image
     *
     * @param cacheId  - any valid URL or cache ID
     * @param angle
     * @return
     */
    @ApiOperation(value="Rotate a cached image")
    @RequestMapping(value = "/{cacheId}/rotate", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<byte[]> rotateImageById(
            @ApiParam(name="cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52") @PathVariable("cacheId") String cacheId
            , @ApiParam(name="angle", required = true, defaultValue = "90") @RequestParam(required = true, defaultValue = "90") Integer angle
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException, InterruptedException, ExecutionException, TimeoutException
    {
        ImageRotateModel args = new ImageRotateModel();
        args.setCacheId(cacheId);
        args.setAngle(angle);

        Future<Map> imageFuture = imageRotateGateway.rotateImage(args);
        Map payload = imageFuture.get(10000, TimeUnit.MILLISECONDS);


        BufferedImage bufferedImage = (BufferedImage)payload.get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)payload.get(ImageConfigMBeanImpl.CONTENT_TYPE);
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
        ImageRotateModel args = new ImageRotateModel();
        args.setFile(file);
        args.setAngle(angle);

        Future<Map> imageFuture = imageRotateGateway.rotateImage(args);
        Map payload = imageFuture.get(10000, TimeUnit.MILLISECONDS);


        BufferedImage bufferedImage = (BufferedImage)payload.get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)payload.get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage( bufferedImage, contentType, returnAsBase64 );
        return result;
    }


    /**
     * Resize an image
     *
     * @param cacheId
     * @param width
     * @param height
     * @param interpolation - highestQuality,highQuality,mediumQuality,highestPerformance,highPerformance,mediumPerformance,nearest,bilinear,bicubic,bessel,blackman,hamming,hanning,hermite,lanczos,mitchell,quadratic
     * @param scaleToFit    (false)
     * @return
     */
    @ApiOperation(value="Resize an cached image")
    @RequestMapping(value = "/{cacheId}/resize", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<byte[]> resizeImageById(
            @ApiParam(name="cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52") @PathVariable("cacheId") String cacheId
            , @ApiParam(name="width", required = true, defaultValue = "200") @RequestParam(required = true) Integer width
            , @ApiParam(name="height", required = true, defaultValue = "200") @RequestParam(required = true) Integer height
            , @ApiParam(name="interpolation", required = false, defaultValue = "bicubic") @RequestParam(required = false, defaultValue = "bicubic") String interpolation
            , @ApiParam(name="scaleToFit", required = false, defaultValue = "false") @RequestParam(required = false, defaultValue = "false") Boolean scaleToFit
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException, InterruptedException, ExecutionException, TimeoutException
    {
        ImageResizeModel args = new ImageResizeModel();
        args.setCacheId(cacheId);
        args.setWidth(width);
        args.setHeight(height);
        args.setInterpolation(interpolation.toUpperCase());
        args.setScaleToFit(scaleToFit);

        Future<Map> imageFuture = imageResizeGateway.resizeImage(args);
        Map payload = imageFuture.get(10000, TimeUnit.MILLISECONDS);


        BufferedImage bufferedImage = (BufferedImage)payload.get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)payload.get(ImageConfigMBeanImpl.CONTENT_TYPE);
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
        ImageResizeModel args = new ImageResizeModel();
        args.setFile(file);
        args.setWidth(width);
        args.setHeight(height);
        args.setInterpolation(interpolation.toUpperCase());
        args.setScaleToFit(scaleToFit);

        Future<Map> imageFuture = imageResizeGateway.resizeImage(args);
        Map payload = imageFuture.get(10000, TimeUnit.MILLISECONDS);


        BufferedImage bufferedImage = (BufferedImage)payload.get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)payload.get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage( bufferedImage, contentType, returnAsBase64 );
        return result;
    }


}
