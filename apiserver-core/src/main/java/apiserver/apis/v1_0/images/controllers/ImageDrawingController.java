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
 * Date: 9/18/12
 */
@Controller
@RequestMapping("/image/draw")
public class ImageDrawingController
{
    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    public HttpChannelInvoker channelInvoker;

    @Autowired
    public MessageChannel imageDrawBorderInputChannel;
    @Autowired
    public MessageChannel imageDrawTextInputChannel;


    /**
     *
     * @param cacheId - any valid URL or cache ID
     * @return  height,width, pixel size, transparency
     */
    @ApiOperation(value = "")
    @RequestMapping(value = "/{cacheId}/border", method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView drawBorderById(
            @ApiParam(name="cacheId", required = true) @PathVariable(value = "cacheId") String cacheId
            , @ApiParam(name="color", required = true) @RequestParam(required = true) String color
            , @ApiParam(name="thickness", required = true) @RequestParam(required = true) Integer thickness )
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.KEY, cacheId);
        args.put(ImageConfigMBeanImpl.COLOR, color);
        args.put(ImageConfigMBeanImpl.THICKNESS, thickness);

        //return channelInvoker.invokeGenericChannel(request, null, args, imageDrawBorderInputChannel);
        throw new RuntimeException("Not implemented exception");
    }


    /**
     *
     * @param file
     * @return   height,width, pixel size, transparency
     */
    @ApiOperation(value = "")
    @RequestMapping(value = "/border", method = {RequestMethod.POST, RequestMethod.PUT})
    public ModelAndView drawBorderByImage(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file
            , @ApiParam(name="color", required = true) @RequestParam(required = true) String color
            , @ApiParam(name="thickness", required = true) @RequestParam(required = true) String thickness )
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.FILE, file);
        args.put(ImageConfigMBeanImpl.COLOR, color);
        args.put(ImageConfigMBeanImpl.THICKNESS, thickness);

        //return channelInvoker.invokeGenericChannel(request, null, args, imageDrawBorderInputChannel);
        throw new RuntimeException("Not implemented exception");
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
     * @param cacheId - any valid URL or cache ID
     * @return  height,width, pixel size, transparency
     */
    @ApiOperation(value = "")
    @RequestMapping(value = "/{cacheId}/watermark", method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView drawWatermarkById(
            @ApiParam(name="cacheId", required = true) @PathVariable(value = "cacheId") String cacheId
            , @ApiParam(name="text", required = true) @RequestParam(required = true) String text
            , @ApiParam(name="color", required = true) @RequestParam(required = true) String color
            , @ApiParam(name="fontSize", required = true) @RequestParam(required = true) String fontSize
            , @ApiParam(name="fontStyle", required = true) @RequestParam(required = true) String fontStyle
            , @ApiParam(name="angle", required = true) @RequestParam(required = true) Integer angle
            , @ApiParam(name="x", required = true) @RequestParam(required = true) Integer x
            , @ApiParam(name="y", required = true) @RequestParam(required = true) Integer y )
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.KEY, cacheId);
        args.put(ImageConfigMBeanImpl.TEXT, text);
        args.put(ImageConfigMBeanImpl.COLOR, color);
        args.put(ImageConfigMBeanImpl.FONT_SIZE, fontSize);
        args.put(ImageConfigMBeanImpl.FONT_STYLE, fontStyle);
        args.put(ImageConfigMBeanImpl.FONT_STYLE, fontStyle);
        args.put(ImageConfigMBeanImpl.ANGLE, angle);
        args.put(ImageConfigMBeanImpl.X, x);
        args.put(ImageConfigMBeanImpl.Y, y);

        //return channelInvoker.invokeGenericChannel(request, null, args, imageDrawBorderInputChannel);
        throw new RuntimeException("Not implemented exception");
    }


    /**
     *
     * @param file
     * @return   height,width, pixel size, transparency
     */
    @ApiOperation(value = "")
    @RequestMapping(value = "/watermark", method = {RequestMethod.POST, RequestMethod.PUT})
    public ModelAndView drawWatermarkByImage(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file
            , @ApiParam(name="text", required = true) @RequestParam(required = true) String text
            , @ApiParam(name="color", required = true) @RequestParam(required = true) String color
            , @ApiParam(name="fontSize", required = true) @RequestParam(required = true) String fontSize
            , @ApiParam(name="fontStyle", required = true) @RequestParam(required = true) String fontStyle
            , @ApiParam(name="angle", required = true) @RequestParam(required = true) Integer angle
            , @ApiParam(name="x", required = true) @RequestParam(required = true) Integer x
            , @ApiParam(name="y", required = true) @RequestParam(required = true) Integer y )
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.FILE, file);
        args.put(ImageConfigMBeanImpl.TEXT, text);
        args.put(ImageConfigMBeanImpl.COLOR, color);
        args.put(ImageConfigMBeanImpl.FONT_SIZE, fontSize);
        args.put(ImageConfigMBeanImpl.FONT_STYLE, fontStyle);
        args.put(ImageConfigMBeanImpl.ANGLE, angle);
        args.put(ImageConfigMBeanImpl.X, x);
        args.put(ImageConfigMBeanImpl.Y, y);

        //return channelInvoker.invokeGenericChannel(request, null, args, imageDrawBorderInputChannel);
        throw new RuntimeException("Not implemented exception");
    }
}
