package apiserver.apis.v1_0.images.controllers;

import apiserver.apis.v1_0.common.HttpChannelInvoker;
import apiserver.apis.v1_0.images.ImageConfigMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
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
     * @param urlOrId - any valid URL or cache ID
     * @param angle
     * @return
     */
    @RequestMapping(value = "/rotate", method = RequestMethod.GET)
    public ModelAndView rotateImageById(
            HttpServletRequest request
            , HttpServletResponse response
            , @RequestParam(required = true) String urlOrId
            , @RequestParam(required = true) Integer angle)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.FILE, urlOrId);
        args.put(ImageConfigMBean.ANGLE, angle);

        return channelInvoker.invokeGenericChannel(request, response, args, imageRotateInputChannel);
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
    public ModelAndView rotateImageByImage(
            HttpServletRequest request
            , HttpServletResponse response
            , @RequestPart("meta-data") Object metadata
            , @RequestPart("file-data") MultipartFile file
            , @RequestParam(required = true) Integer angle)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.FILE, file);
        args.put(ImageConfigMBean.ANGLE, angle);

        return channelInvoker.invokeGenericChannel(request, response, args, imageRotateInputChannel);
    }



    /**
     * Resize an image
     *
     * @param request
     * @param response
     * @param id
     * @param interpolation - highestQuality,highQuality,mediumQuality,highestPerformance,highPerformance,mediumPerformance,nearest,bilinear,bicubic,bessel,blackman,hamming,hanning,hermite,lanczos,mitchell,quadratic
     * @param width
     * @param height
     * @return
     */
    @RequestMapping(value = "/resize", method = RequestMethod.POST)
    public ModelAndView resizeImageById(
            HttpServletRequest request
            , HttpServletResponse response
            , @RequestParam(required = true) String id
            , @RequestParam(required = false, defaultValue = "bicubic") String interpolation
            , @RequestParam(required = true) Integer width
            , @RequestParam(required = true) Integer height
            , @RequestParam(required = false, defaultValue = "false") Boolean scaleToFit)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.KEY, id);
        args.put(ImageConfigMBean.INTERPOLATION, interpolation);
        args.put(ImageConfigMBean.WIDTH, width);
        args.put(ImageConfigMBean.HEIGHT, height);
        args.put(ImageConfigMBean.SCALE_TO_FIT, scaleToFit);

        return channelInvoker.invokeGenericChannel(request, response, args, imageResizeInputChannel);
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
    public ModelAndView resizeImageByImage(
            HttpServletRequest request
            , HttpServletResponse response
            , @RequestPart("meta-data") Object metadata
            , @RequestPart("file-data") MultipartFile file
            , @RequestParam(required = false, defaultValue = "bicubic") String interpolation
            , @RequestParam(required = true) Integer width
            , @RequestParam(required = true) Integer height
            , @RequestParam(required = false, defaultValue = "false") Boolean scaleToFit)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.FILE, file);
        args.put(ImageConfigMBean.INTERPOLATION, interpolation);
        args.put(ImageConfigMBean.WIDTH, width);
        args.put(ImageConfigMBean.HEIGHT, height);
        args.put(ImageConfigMBean.SCALE_TO_FIT, scaleToFit);

        return channelInvoker.invokeGenericChannel(request, response, args, imageResizeInputChannel);
    }


}
