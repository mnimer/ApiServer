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
 * Date: 9/18/12
 */
@Controller
@RequestMapping("/image/draw")
public class ImageDrawingController
{

    @Autowired
    public HttpChannelInvoker channelInvoker;

    @Autowired
    public MessageChannel imageDrawBorderInputChannel;
    @Autowired
    public MessageChannel imageDrawTextInputChannel;


    /**
     *
     * @param request
     * @param response
     * @param urlOrId - any valid URL or cache ID
     * @return  height,width, pixel size, transparency
     */
    @RequestMapping(value = "/border", method = RequestMethod.GET)
    public ModelAndView drawBorderById(
            HttpServletRequest request
            , HttpServletResponse response
            , @RequestParam(required = true) String urlOrId
            , @RequestParam(required = true) String color
            , @RequestParam(required = true) Integer thickness )
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.KEY, urlOrId);
        args.put(ImageConfigMBean.COLOR, color);
        args.put(ImageConfigMBean.THICKNESS, thickness);

        return channelInvoker.invokeGenericChannel(request, response, args, imageDrawBorderInputChannel);
    }


    /**
     *
     * @param request
     * @param response
     * @param file
     * @return   height,width, pixel size, transparency
     */
    @RequestMapping(value = "/border", method = RequestMethod.POST)
    public ModelAndView drawBorderByImage(
            HttpServletRequest request
            , HttpServletResponse response
            , @RequestPart("meta-data") Object metadata
            , @RequestPart("file-data") MultipartFile file
            , @RequestParam(required = true) String color
            , @RequestParam(required = true) String thickness )
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.FILE, file);
        args.put(ImageConfigMBean.COLOR, color);
        args.put(ImageConfigMBean.THICKNESS, thickness);

        return channelInvoker.invokeGenericChannel(request, response, args, imageDrawBorderInputChannel);
    }



    /**
     *
     * <cfset ImageSetDrawingColor(myImage, "white")>
     <cfset attr=StructNew()>
     <cfset attr.size=50>
     <cfset attr.style="bold">
     <cfset ImageDrawText(myImage, watermark,650,610, attr)>
     *
     *
     * @param request
     * @param response
     * @param urlOrId - any valid URL or cache ID
     * @return  height,width, pixel size, transparency
     */
    @RequestMapping(value = "/watermark", method = RequestMethod.GET)
    public ModelAndView drawWatermarkById(
            HttpServletRequest request
            , HttpServletResponse response
            , @RequestParam(required = true) String urlOrId
            , @RequestParam(required = true) String text
            , @RequestParam(required = true) String color
            , @RequestParam(required = true) String fontSize
            , @RequestParam(required = true) String fontStyle
            , @RequestParam(required = true) Integer angle
            , @RequestParam(required = true) Integer x
            , @RequestParam(required = true) Integer y )
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.KEY, urlOrId);
        args.put(ImageConfigMBean.TEXT, text);
        args.put(ImageConfigMBean.COLOR, color);
        args.put(ImageConfigMBean.FONT_SIZE, fontSize);
        args.put(ImageConfigMBean.FONT_STYLE, fontStyle);
        args.put(ImageConfigMBean.FONT_STYLE, fontStyle);
        args.put(ImageConfigMBean.ANGLE, angle);
        args.put(ImageConfigMBean.X, x);
        args.put(ImageConfigMBean.Y, y);

        return channelInvoker.invokeGenericChannel(request, response, args, imageDrawBorderInputChannel);
    }


    /**
     *
     * @param request
     * @param response
     * @param file
     * @return   height,width, pixel size, transparency
     */
    @RequestMapping(value = "/watermark", method = RequestMethod.POST)
    public ModelAndView drawWatermarkByImage(
            HttpServletRequest request
            , HttpServletResponse response
            , @RequestPart("meta-data") Object metadata
            , @RequestPart("file-data") MultipartFile file
            , @RequestParam(required = true) String text
            , @RequestParam(required = true) String color
            , @RequestParam(required = true) String fontSize
            , @RequestParam(required = true) String fontStyle
            , @RequestParam(required = false) Integer angle
            , @RequestParam(required = true) Integer x
            , @RequestParam(required = true) Integer y)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.FILE, file);
        args.put(ImageConfigMBean.TEXT, text);
        args.put(ImageConfigMBean.COLOR, color);
        args.put(ImageConfigMBean.FONT_SIZE, fontSize);
        args.put(ImageConfigMBean.FONT_STYLE, fontStyle);
        args.put(ImageConfigMBean.ANGLE, angle);
        args.put(ImageConfigMBean.X, x);
        args.put(ImageConfigMBean.Y, y);

        return channelInvoker.invokeGenericChannel(request, response, args, imageDrawBorderInputChannel);
    }
}
