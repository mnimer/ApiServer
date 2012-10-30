package apiserver.apis.v1_0.images.controllers;

import apiserver.apis.v1_0.common.HttpChannelInvoker;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import coldfusion.image.Image;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.core.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

/**
 * User: mnimer
 * Date: 9/15/12
 */

@Controller
@RequestMapping("/image")
public class ImageManipulationController
{
    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    public HttpChannelInvoker channelInvoker;

    @Autowired
    public MessageChannel imageRotateInputChannel;
    @Autowired
    public MessageChannel imageResizeInputChannel;


    /**
     * rotate an image
     *
     * @param cacheId  - any valid URL or cache ID
     * @param angle
     * @return
     */
    @ApiOperation(value="Rotate a cached image")
    @RequestMapping(value = "/{cacheId}/rotate", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<byte[]> rotateImageById(
            @ApiParam(name="cacheId", required = true) @PathVariable("cacheId") String cacheId
            , @ApiParam(name="angle", required = true) @RequestParam(required = true) Integer angle
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.KEY, cacheId);
        args.put(ImageConfigMBeanImpl.ANGLE, angle);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageRotateInputChannel);

        BufferedImage bufferedImage = ((Image)view.getModel().get("result")).getCurrentImage();
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( bufferedImage, contentType, returnAsBase64 );
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
    @RequestMapping(value = "/rotate", method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public ResponseEntity<byte[]> rotateImageByImage(
            @ApiParam(name="file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name="angle", required = true) @RequestParam(required = true) Integer angle
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.FILE, file);
        args.put(ImageConfigMBeanImpl.ANGLE, angle);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageRotateInputChannel);

        BufferedImage bufferedImage = ((Image)view.getModel().get("result")).getCurrentImage();
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( bufferedImage, contentType, returnAsBase64 );
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
    @RequestMapping(value = "/{cacheId}/resize", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<byte[]> resizeImageById(
            @ApiParam(name="cacheId", required = true) @PathVariable("cacheId") String cacheId
            , @ApiParam(name="width", required = true) @RequestParam(required = true) Integer width
            , @ApiParam(name="height", required = true) @RequestParam(required = true) Integer height
            , @ApiParam(name="interpolation", required = false, defaultValue = "bicubic") @RequestParam(required = false, defaultValue = "bicubic") String interpolation
            , @ApiParam(name="scaleToFit", required = false, defaultValue = "false") @RequestParam(required = false, defaultValue = "false") Boolean scaleToFit
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.KEY, cacheId);
        args.put(ImageConfigMBeanImpl.WIDTH, width);
        args.put(ImageConfigMBeanImpl.HEIGHT, height);
        args.put(ImageConfigMBeanImpl.INTERPOLATION, interpolation.toUpperCase());
        args.put(ImageConfigMBeanImpl.SCALE_TO_FIT, scaleToFit);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageResizeInputChannel);


        BufferedImage bufferedImage = ((Image)view.getModel().get("result")).getCurrentImage();
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( bufferedImage, contentType, returnAsBase64 );
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
    @RequestMapping(value = "/resize", method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public ResponseEntity<byte[]> resizeImageByImage(
            @ApiParam(name="file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name="width", required = true) @RequestParam(required = true) Integer width
            , @ApiParam(name="height", required = true) @RequestParam(required = true) Integer height
            , @ApiParam(name="interpolation", required = false, defaultValue = "bicubic") @RequestParam(required = false, defaultValue = "bicubic") String interpolation
            , @ApiParam(name="scaleToFit", required = false, defaultValue = "false") @RequestParam(required = false, defaultValue = "false") Boolean scaleToFit
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.FILE, file);
        args.put(ImageConfigMBeanImpl.INTERPOLATION, interpolation);
        args.put(ImageConfigMBeanImpl.WIDTH, width);
        args.put(ImageConfigMBeanImpl.HEIGHT, height);
        args.put(ImageConfigMBeanImpl.SCALE_TO_FIT, scaleToFit);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageResizeInputChannel);


        BufferedImage bufferedImage = ((Image)view.getModel().get("result")).getCurrentImage();
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( bufferedImage, contentType, returnAsBase64 );
        return result;
    }


}
