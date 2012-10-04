package apiserver.apis.v1_0.images.controllers;

import apiserver.apis.v1_0.common.HttpChannelInvoker;
import apiserver.apis.v1_0.images.ImageConfigMBean;
import com.wordnik.swagger.core.Api;
import com.wordnik.swagger.core.ApiOperation;
import com.wordnik.swagger.core.ApiParam;
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
    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    public HttpChannelInvoker channelInvoker;

    @Autowired
    public MessageChannel imageSizeInputChannel;
    @Autowired
    public MessageChannel imageMetadataInputChannel;


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
     * @param cacheId - any valid URL or cache ID
     * @return  height,width, pixel size, transparency
     */
    @ApiOperation(value = "Get the height and width for the image", responseClass = "java.util.Map")
    @RequestMapping(value = "/{cacheId}/size", method =  {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView imageInfoById(
            @ApiParam(value = "cache id returned by /image-cache/add", required = true) @PathVariable("cacheId") String cacheId)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.KEY, cacheId);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageSizeInputChannel);
        return view;
    }


    /**
     * get basic info
     * @param file
     * @return   height,width, pixel size, transparency
     * , @RequestPart("meta-data") Object metadata
     *
    , @RequestParam MultipartFile file

     */
    @ApiOperation(value = "Get the height and width for the image", responseClass = "java.util.Map")
    @RequestMapping(value = "/size", method = {RequestMethod.POST, RequestMethod.PUT})
    public ModelAndView imageInfoByImage(
            @ApiParam(value = "uploaded file to process", required = true) @RequestParam("file") MultipartFile file)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.FILE, file);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageSizeInputChannel);

        return view;
    }



    /**
     * get embedded metadata
     * @param cacheId - any valid URL or cache ID
     * @return  height,width, pixel size, transparency
     */
    @ApiOperation(value = "Get the embedded metadata", responseClass = "java.util.Map")
    @RequestMapping(value = "/{cacheId}/metadata", method =  {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView imageMetadataById(
            @ApiParam(value = "cache id returned by /image-cache/add", required = true) @PathVariable("cacheId") String cacheId)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.KEY, cacheId);

        return channelInvoker.invokeGenericChannel(request, null, args, imageMetadataInputChannel);
    }


    /**
     * get embedded metadata
     * @param file
     * @return   height,width, pixel size, transparency
     */
    @ApiOperation(value = "Get the embedded metadata", responseClass = "java.util.Map")
    @RequestMapping(value = "/metadata", method = {RequestMethod.POST, RequestMethod.PUT})
    public ModelAndView imageMetadataByImage(
            @ApiParam(value = "uploaded file to process", required = true) @RequestParam("file") MultipartFile file )
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.FILE, file);

        return channelInvoker.invokeGenericChannel(request, null, args, imageMetadataInputChannel);
    }



}
