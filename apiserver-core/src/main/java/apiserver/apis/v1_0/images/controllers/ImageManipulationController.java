package apiserver.apis.v1_0.images.controllers;

import apiserver.apis.v1_0.common.HttpChannelInvoker;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.core.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
    public byte[] rotateImageById(
            @ApiParam(name="cacheId", required = true) @PathVariable("cacheId") String cacheId
            , @ApiParam(name="angle", required = true) @RequestParam(required = true) Integer angle)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.KEY, cacheId);
        args.put(ImageConfigMBeanImpl.ANGLE, angle);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageRotateInputChannel);
        return (byte[]) view.getModel().get("image");
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
    public byte[] rotateImageByImage(
            @ApiParam(name="file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name="angle", required = true) @RequestParam(required = true) Integer angle)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.FILE, file);
        args.put(ImageConfigMBeanImpl.ANGLE, angle);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageRotateInputChannel);

        return (byte[]) view.getModel().get("image");
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
    public byte[] resizeImageById(
            @ApiParam(name="cacheId", required = true) @PathVariable("cacheId") String cacheId
            , @ApiParam(name="width", required = true) @RequestParam(required = true) Integer width
            , @ApiParam(name="height", required = true) @RequestParam(required = true) Integer height
            , @ApiParam(name="interpolation", required = false, defaultValue = "bicubic") @RequestParam(required = false, defaultValue = "bicubic") String interpolation
            , @ApiParam(name="scaleToFit", required = false, defaultValue = "false") @RequestParam(required = false, defaultValue = "false") Boolean scaleToFit)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.KEY, cacheId);
        args.put(ImageConfigMBeanImpl.WIDTH, width);
        args.put(ImageConfigMBeanImpl.HEIGHT, height);
        args.put(ImageConfigMBeanImpl.INTERPOLATION, interpolation.toUpperCase());
        args.put(ImageConfigMBeanImpl.SCALE_TO_FIT, scaleToFit);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageResizeInputChannel);
        return (byte[]) view.getModel().get("image");
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
    public byte[] resizeImageByImage(
            @ApiParam(name="file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name="width", required = true) @RequestParam(required = true) Integer width
            , @ApiParam(name="height", required = true) @RequestParam(required = true) Integer height
            , @ApiParam(name="interpolation", required = false, defaultValue = "bicubic") @RequestParam(required = false, defaultValue = "bicubic") String interpolation
            , @ApiParam(name="scaleToFit", required = false, defaultValue = "false") @RequestParam(required = false, defaultValue = "false") Boolean scaleToFit)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.FILE, file);
        args.put(ImageConfigMBeanImpl.INTERPOLATION, interpolation);
        args.put(ImageConfigMBeanImpl.WIDTH, width);
        args.put(ImageConfigMBeanImpl.HEIGHT, height);
        args.put(ImageConfigMBeanImpl.SCALE_TO_FIT, scaleToFit);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageResizeInputChannel);

        return (byte[]) view.getModel().get("image");
    }


}
