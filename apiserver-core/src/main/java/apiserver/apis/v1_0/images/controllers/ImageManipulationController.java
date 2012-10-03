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
    @Autowired
    public HttpChannelInvoker channelInvoker;

   @Autowired
    public MessageChannel imageRotateInputChannel;
    @Autowired
    public MessageChannel imageResizeInputChannel;


    /**
     * rotate an image
     * @param request
     * @param response
     * @param cacheId - any valid URL or cache ID
     * @param angle
     * @return
     */
    @RequestMapping(value = "/{id}/rotate", method = RequestMethod.GET)
    @ResponseBody
    public byte[] rotateImageById(
            HttpServletRequest request
            , HttpServletResponse response
            , @PathVariable("id") String cacheId
            , @RequestParam(required = true) Integer angle)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.FILE, cacheId);
        args.put(ImageConfigMBean.ANGLE, angle);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, response, args, imageRotateInputChannel);
        return (byte[])view.getModel().get("image");
    }


    /**
     * rotate an image
     * @param request
     * @param response
     * @param file
     * @param angle
     * @return
     */
    @RequestMapping(value = "/rotate", method = RequestMethod.POST)
    @ResponseBody
    public byte[] rotateImageByImage(
            HttpServletRequest request
            , HttpServletResponse response
            , @RequestParam MultipartFile file
            , @RequestParam(required = true) Integer angle)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.FILE, file);
        args.put(ImageConfigMBean.ANGLE, angle);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, response, args, imageRotateInputChannel);

        return (byte[])view.getModel().get("image");
    }



    /**
     * Resize an image
     *
     * @param request
     * @param response
     * @param cacheId
     * @param width
     * @param height
     * @param interpolation - highestQuality,highQuality,mediumQuality,highestPerformance,highPerformance,mediumPerformance,nearest,bilinear,bicubic,bessel,blackman,hamming,hanning,hermite,lanczos,mitchell,quadratic
     * @param scaleToFit (false)
     * @return
     */
    @RequestMapping(value = "/{id}/resize", method = RequestMethod.POST)
    @ResponseBody
    public byte[] resizeImageById(
            HttpServletRequest request
            , HttpServletResponse response
            , @PathVariable("id") String cacheId
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

        ModelAndView view = channelInvoker.invokeGenericChannel(request, response, args, imageResizeInputChannel);
        return (byte[])view.getModel().get("image");
    }



    /**
     * Resize an image
     *
     * @param request
     * @param response
     * @param file
     * @param interpolation - highestQuality,highQuality,mediumQuality,highestPerformance,highPerformance,mediumPerformance,nearest,bilinear,bicubic,bessel,blackman,hamming,hanning,hermite,lanczos,mitchell,quadratic
     * @param width
     * @param height
     * @return
     */
    @RequestMapping(value = "/resize", method = RequestMethod.POST)
    @ResponseBody
    public byte[] resizeImageByImage(
            HttpServletRequest request
            , HttpServletResponse response
            , @RequestParam MultipartFile file
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

        ModelAndView view = channelInvoker.invokeGenericChannel(request, response, args, imageResizeInputChannel);

        return (byte[])view.getModel().get("image");
    }


}
