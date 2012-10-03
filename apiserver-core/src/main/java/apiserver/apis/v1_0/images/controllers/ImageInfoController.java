package apiserver.apis.v1_0.images.controllers;

import apiserver.apis.v1_0.common.HttpChannelInvoker;
import apiserver.apis.v1_0.images.ImageConfigMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/15/12
 */

@Controller
@RequestMapping("/image-info")
public class ImageInfoController
{
    @Autowired
    public HttpChannelInvoker channelInvoker;

    @Autowired
    public MessageChannel imageSizeInputChannel;
    @Autowired
    public MessageChannel imageMetadataInputChannel;


    //TODO: Add ImageGrayscale
    //TODO: Add ImageNegative
    //TODO: Add ImageOverlay
    //TODO: Add ImageFlip
    //TODO: Add ImageSharpen
    //TODO: Add ImageShear, ImageShearDrawingAxis
    //TODO: Add ImageRotateDrawingAxis
    //TODO: Add Image Drawing Support (array of actions; ImageDrawLine, ImageDrawOval, ImageDrawPoint, ImageDrawQuadraticCurve
    //TODO add to /metadata - ImageGetEXIFTag
    //TODO add to /metadata - ImageGetIPTCTag

    /**
     * get basic info
     * @param request
     * @param response
     * @param id - any valid URL or cache ID
     * @return  height,width, pixel size, transparency
     */
    @RequestMapping(value = "/{id}/size", method = RequestMethod.GET)
    public ModelAndView imageInfoById(
            HttpServletRequest request
            , HttpServletResponse response
            , @PathVariable("id") String cacheId)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.KEY, cacheId);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, response, args, imageSizeInputChannel);
        return view;
    }


    /**
     * get basic info
     * @param request
     * @param response
     * @param file
     * @return   height,width, pixel size, transparency
     * , @RequestPart("meta-data") Object metadata
     *
    , @RequestParam MultipartFile file

     */
    @RequestMapping(value = "/size", method = RequestMethod.POST)
    public ModelAndView imageInfoByImage(
            MultipartHttpServletRequest request
            , HttpServletResponse response
            , @RequestParam MultipartFile file)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.FILE, file);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, response, args, imageSizeInputChannel);

        return view;
    }



    /**
     * get embedded metadata
     * @param request
     * @param response
     * @param urlOrId - any valid URL or cache ID
     * @return  height,width, pixel size, transparency
     */
    @RequestMapping(value = "/{id}/metadata", method = RequestMethod.GET)
    public ModelAndView imageMetadataById(
            HttpServletRequest request
            , HttpServletResponse response
            , @PathVariable("id") String cacheId)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.KEY, cacheId);

        return channelInvoker.invokeGenericChannel(request, response, args, imageMetadataInputChannel);
    }


    /**
     * get embedded metadata
     * @param request
     * @param response
     * @param file
     * @return   height,width, pixel size, transparency
     */
    @RequestMapping(value = "/metadata", method = RequestMethod.POST)
    public ModelAndView imageMetadataByImage(
            HttpServletRequest request
            , HttpServletResponse response
            , @RequestParam MultipartFile file )
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.FILE, file);

        return channelInvoker.invokeGenericChannel(request, response, args, imageMetadataInputChannel);
    }



}
