package apiserver.apis.v1_0.images.controllers;

import apiserver.ApiServerConstants;
import apiserver.apis.v1_0.common.HttpChannelInvoker;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import coldfusion.image.Image;
import com.adobe.xmp.impl.Base64;
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

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/27/12
 */

@Controller
@RequestMapping("/image/filters")
public class ImageFiltersController
{
    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired(required = false)
    private HttpServletResponse response;

    @Autowired
    public HttpChannelInvoker channelInvoker;

    @Autowired
    private MessageChannel imageBoxBlurFilterChannel;


    @ApiOperation(value = "")
    @RequestMapping(value = "/{cacheId}/grayscale", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView imageInfoById(
            @ApiParam(name = "cacheId", required = true) @PathVariable("cacheId") String cacheId)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.KEY, cacheId);

        throw new RuntimeException("not implemented yet");

    }


    @ApiOperation(value = "")
    @RequestMapping(value = "/grayscale", method = {RequestMethod.POST, RequestMethod.PUT})
    public ModelAndView imageInfoById(
            @ApiParam(name = "file", required = true) @RequestParam MultipartFile file)
    {
        Map<String, Object> args = new HashMap<String, Object>();

        throw new RuntimeException("not implemented yet");
    }


    /**
     *
     *  http://www.jhlabs.com/ Image Filters
     *
     */

    /**
     * A filter which performs a box blur on an image. The horizontal and vertical blurs can be specified separately and a number of iterations can be given which allows an approximation to Gaussian blur.
     *
     * @param cacheId
     * @param hRadius
     * @param vRadius
     * @param iterations
     * @param premultiplyAlpha
     * @param returnAsBase64
     * @return image
     */
    @ApiOperation(value = "A filter which performs a box blur on an image. The horizontal and vertical blurs can be specified separately and a number of iterations can be given which allows an approximation to Gaussian blur.")
    @RequestMapping(value = "/{cacheId}/boxblur", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> imageInfoById(
            @ApiParam(name = "cacheId", required = true)  @PathVariable("cacheId") String cacheId
            , @ApiParam(name = "hRadius", required = false, defaultValue = "2", value = "the horizontal radius of blur") @RequestParam(value = "hRadius", defaultValue = "2") int hRadius
            , @ApiParam(name = "vRadius", required = false, defaultValue = "2", value = "the vertical radius of blur") @RequestParam(value = "vRadius", defaultValue = "2") int vRadius
            , @ApiParam(name = "iterations", required = false, defaultValue = "1", value = "the number of time to iterate the blur") @RequestParam(value = "interations", defaultValue = "1") int iterations
            , @ApiParam(name = "preMultiplyAlpha", required = false, defaultValue = "true", allowableValues = "true,false", value = "pre multiply the alpha channel") @RequestParam(value = "premultiplyAlpha", defaultValue = "true") boolean premultiplyAlpha
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map args = new LinkedHashMap();
        args.put(ApiServerConstants.HTTP_REQUEST, request);
        args.put(ApiServerConstants.HTTP_RESPONSE, response);
        args.put(ImageConfigMBeanImpl.KEY, cacheId);
        args.put("hRadius", hRadius);
        args.put("vRadius", vRadius);
        args.put("iterations", iterations);
        args.put("preMultiplyAlpha", premultiplyAlpha);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageBoxBlurFilterChannel);

        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( bufferedImage, contentType, returnAsBase64 );
        return result;
    }



    /**
     * A filter which performs a box blur on an image. The horizontal and vertical blurs can be specified separately and a number of iterations can be given which allows an approximation to Gaussian blur.
     *
     * @param file
     * @param hRadius
     * @param vRadius
     * @param iterations
     * @param premultiplyAlpha
     * @param returnAsBase64
     * @return image
     */
    @ApiOperation(value = "A filter which performs a box blur on an image. The horizontal and vertical blurs can be specified separately and a number of iterations can be given which allows an approximation to Gaussian blur.")
    @RequestMapping(value = "/boxblur", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<byte[]> imageInfoById(
            @ApiParam(name = "file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name = "hRadius", required = false, defaultValue = "2", value = "the horizontal radius of blur") @RequestParam(value = "hRadius", defaultValue = "2") int hRadius
            , @ApiParam(name = "vRadius", required = false, defaultValue = "2", value = "the vertical radius of blur") @RequestParam(value = "vRadius", defaultValue = "2") int vRadius
            , @ApiParam(name = "iterations", required = false, defaultValue = "1", value = "the number of time to iterate the blur") @RequestParam(value = "interations", defaultValue = "1") int iterations
            , @ApiParam(name = "preMultiplyAlpha", required = false, defaultValue = "true", allowableValues = "true,false", value = "pre multiply the alpha channel") @RequestParam(value = "premultiplyAlpha", defaultValue = "true") boolean premultiplyAlpha
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map args = new LinkedHashMap();
        args.put(ApiServerConstants.HTTP_REQUEST, request);
        args.put(ApiServerConstants.HTTP_RESPONSE, response);
        args.put(ImageConfigMBeanImpl.FILE, file);
        args.put("hRadius", hRadius);
        args.put("vRadius", vRadius);
        args.put("iterations", iterations);
        args.put("preMultiplyAlpha", premultiplyAlpha);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageBoxBlurFilterChannel);


        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler(  bufferedImage, contentType, returnAsBase64);
        return result;
    }

}
