package apiserver.apis.v1_0.images.controllers;

import apiserver.apis.v1_0.common.HttpChannelInvoker;
import apiserver.apis.v1_0.images.ImageConfigMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @RequestMapping(value = "/{cacheId}/rotate", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public byte[] rotateImageById(
            @PathVariable("cacheId") String cacheId
            , @RequestParam(required = true) Integer angle)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.FILE, cacheId);
        args.put(ImageConfigMBean.ANGLE, angle);

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
    @RequestMapping(value = "/rotate", method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public byte[] rotateImageByImage(
            @RequestParam MultipartFile file
            , @RequestParam(required = true) Integer angle)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.FILE, file);
        args.put(ImageConfigMBean.ANGLE, angle);

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
    @RequestMapping(value = "/{cacheId}/resize", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public byte[] resizeImageById(
            @PathVariable("cacheId") String cacheId
            , @RequestParam(required = true) Integer width
            , @RequestParam(required = true) Integer height
            , @RequestParam(required = false, defaultValue = "bicubic") String interpolation
            , @RequestParam(required = false, defaultValue = "false") Boolean scaleToFit)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.KEY, cacheId);
        args.put(ImageConfigMBean.WIDTH, width);
        args.put(ImageConfigMBean.HEIGHT, height);
        args.put(ImageConfigMBean.INTERPOLATION, interpolation);
        args.put(ImageConfigMBean.SCALE_TO_FIT, scaleToFit);

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
    @RequestMapping(value = "/resize", method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public byte[] resizeImageByImage(
            @RequestParam MultipartFile file
            , @RequestParam(required = true) Integer width
            , @RequestParam(required = true) Integer height
            , @RequestParam(required = false, defaultValue = "bicubic") String interpolation
            , @RequestParam(required = false, defaultValue = "false") Boolean scaleToFit)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.FILE, file);
        args.put(ImageConfigMBean.INTERPOLATION, interpolation);
        args.put(ImageConfigMBean.WIDTH, width);
        args.put(ImageConfigMBean.HEIGHT, height);
        args.put(ImageConfigMBean.SCALE_TO_FIT, scaleToFit);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageResizeInputChannel);

        return (byte[]) view.getModel().get("image");
    }


}
