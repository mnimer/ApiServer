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
@RequestMapping("/image/info")
public class ImageInfoController
{
    @Autowired
    public HttpChannelInvoker channelInvoker;

    @Autowired
    public MessageChannel imageInfoInputChannel;
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
     * @param urlOrId - any valid URL or cache ID
     * @return  height,width, pixel size, transparency
     */
    @RequestMapping(value = "/size2", method = RequestMethod.GET)
    public ModelAndView imageInfoById(
            HttpServletRequest request
            , HttpServletResponse response
            , @RequestParam(required = true) String urlOrId)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.KEY, urlOrId);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, response, args, imageInfoInputChannel);
        return view;
    }


    /**
     * get basic info
     * @param request
     * @param response
     * @param file
     * @return   height,width, pixel size, transparency
     * , @RequestPart("meta-data") Object metadata
     */
    @RequestMapping(value = "/size", method = RequestMethod.POST)
    public ModelAndView imageInfoByImage(
            HttpServletRequest request
            , HttpServletResponse response
            , @RequestParam MultipartFile file)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.FILE, file);

        return channelInvoker.invokeGenericChannel(request, response, args, imageInfoInputChannel);
    }



    /**
     * get embedded metadata
     * @param request
     * @param response
     * @param urlOrId - any valid URL or cache ID
     * @return  height,width, pixel size, transparency
     */
    @RequestMapping(value = "/metadata2", method = RequestMethod.GET)
    public ModelAndView imageMetadataById(
            HttpServletRequest request
            , HttpServletResponse response
            , @RequestParam(required = true) String urlOrId)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.KEY, urlOrId);

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
            , @RequestPart("meta-data") Object metadata
            , @RequestPart("file-data") MultipartFile file)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.FILE, file);

        return channelInvoker.invokeGenericChannel(request, response, args, imageMetadataInputChannel);
    }



}
