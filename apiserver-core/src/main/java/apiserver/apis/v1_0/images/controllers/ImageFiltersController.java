package apiserver.apis.v1_0.images.controllers;

import apiserver.ApiServerConstants;
import apiserver.apis.v1_0.common.HttpChannelInvoker;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * http://www.jhlabs.com/ Image Filters
 *
 * User: mnimer
 * Date: 9/27/12
 */
@Controller
@RequestMapping("/image-filters")
public class ImageFiltersController
{
    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired(required = false)
    private HttpServletResponse response;

    @Autowired
    public HttpChannelInvoker channelInvoker;



    @Autowired @Qualifier("imageBlurFilterChannel")
    private MessageChannel imageBlurFilterChannel;

    @Autowired @Qualifier("imageBoxBlurFilterChannel")
    private MessageChannel imageBoxBlurFilterChannel;

    @Autowired @Qualifier("imageBumpFilterChannel")
    private MessageChannel imageBumpFilterChannel;

    @Autowired @Qualifier("imageDespeckleFilterChannel")
    private MessageChannel imageDespeckleFilterChannel;

    @Autowired @Qualifier("imageGlowFilterChannel")
    private MessageChannel imageGlowFilterChannel;

    @Autowired @Qualifier("imageGrayScaleFilterChannel")
    private MessageChannel imageGrayScaleFilterChannel;

    @Autowired @Qualifier("imageMaskFilterChannel")
    private MessageChannel imageMaskFilterChannel;

    @Autowired @Qualifier("imageGaussianFilterChannel")
    private MessageChannel imageGaussianFilterChannel;

    @Autowired @Qualifier("imageLensBlurFilterChannel")
    private MessageChannel imageLensBlurFilterChannel;

    @Autowired @Qualifier("imageMaximumFilterChannel")
    private MessageChannel imageMaximumFilterChannel;

    @Autowired @Qualifier("imageMedianFilterChannel")
    private MessageChannel imageMedianFilterChannel;

    @Autowired @Qualifier("imageMinimumFilterChannel")
    private MessageChannel imageMinimumFilterChannel;

    @Autowired @Qualifier("imageMotionBlurFilterChannel")
    private MessageChannel imageMotionBlurFilterChannel;

    @Autowired @Qualifier("imageOilFilterChannel")
    private MessageChannel imageOilFilterChannel;



    /**
     * A filter which performs a box blur on an image. The horizontal and vertical blurs can be specified separately and a number of iterations can be given which allows an approximation to Gaussian blur.
     *
     * @param cacheId
     * @param returnAsBase64
     * @return image
     */
    @ApiOperation(value = "This filter blurs an image very slightly using a 3x3 blur kernel. ")
    @RequestMapping(value = "/{cacheId}/blur", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> imageBlurById(
            @ApiParam(name = "cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52")  @PathVariable("cacheId") String cacheId
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map args = new HashMap();
        args.put(ApiServerConstants.HTTP_REQUEST, request);
        args.put(ApiServerConstants.HTTP_RESPONSE, response);
        args.put(ImageConfigMBeanImpl.KEY, cacheId);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageBlurFilterChannel);

        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( bufferedImage, contentType, returnAsBase64 );
        return result;
    }



    /**
     * This filter blurs an image very slightly using a 3x3 blur kernel.
     *
     * @param file
     * @param returnAsBase64
     * @return image
     */
    @ApiOperation(value = "This filter blurs an image very slightly using a 3x3 blur kernel.")
    @RequestMapping(value = "/blur", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<byte[]> imageBlurByFile(
            @ApiParam(name = "file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map args = new LinkedHashMap();
        args.put(ApiServerConstants.HTTP_REQUEST, request);
        args.put(ApiServerConstants.HTTP_RESPONSE, response);
        args.put(ImageConfigMBeanImpl.FILE, file);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageBlurFilterChannel);


        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler(  bufferedImage, contentType, returnAsBase64);
        return result;
    }




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
    public ResponseEntity<byte[]> imageBoxBlurById(
            @ApiParam(name = "cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52")  @PathVariable("cacheId") String cacheId
            , @ApiParam(name = "hRadius", required = false, defaultValue = "2", value = "the horizontal radius of blur") @RequestParam(value = "hRadius", defaultValue = "2") int hRadius
            , @ApiParam(name = "vRadius", required = false, defaultValue = "2", value = "the vertical radius of blur") @RequestParam(value = "vRadius", defaultValue = "2") int vRadius
            , @ApiParam(name = "iterations", required = false, defaultValue = "1", value = "the number of time to iterate the blur") @RequestParam(value = "interations", defaultValue = "1") int iterations
            , @ApiParam(name = "preMultiplyAlpha", required = false, defaultValue = "true", allowableValues = "true,false", value = "pre multiply the alpha channel") @RequestParam(value = "premultiplyAlpha", defaultValue = "true") boolean premultiplyAlpha
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map args = new HashMap();
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
    public ResponseEntity<byte[]> imageBoxBlurByFile(
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




    @ApiOperation(value = "This filter does a simple convolution which emphasises edges in an image.")
    @RequestMapping(value = "/{cacheId}/bump", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> imageBumpById(
            @ApiParam(name = "cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52") @PathVariable("cacheId") String cacheId
            , @ApiParam(name = "edgeAction", required = false, defaultValue = "1") @RequestParam(value = "edgeAction", defaultValue = "1") int edgeAction
            , @ApiParam(name = "useAlpha", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "useAlpha", defaultValue = "true") boolean useAlpha
            , @ApiParam(name = "matrix", required = false, defaultValue = "-1.0,-1.0,0.0,-1.0,1.0,1.0,0.0,1.0,1.0") @RequestParam(value = "matrix", defaultValue = "-1.0,-1.0,0.0,-1.0,1.0,1.0,0.0,1.0,1.0") String matrix
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        // convert string array into float array
        String[] matrixStrings = matrix.split(",");
        float[] matrixValues = new float[matrixStrings.length];
        for (int i = 0; i < matrixStrings.length; i++)
        {
            String s = matrixStrings[i];
            matrixValues[i] = Float.parseFloat(s);
        }


        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.KEY, cacheId);
        args.put("edgeAction", edgeAction);
        args.put("useAlpha", useAlpha);
        args.put("matrix", matrixValues);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageBumpFilterChannel);

        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( bufferedImage, contentType, returnAsBase64 );
        return result;

    }


    @ApiOperation(value = "This filter does a simple convolution which emphasises edges in an image.")
    @RequestMapping(value = "/bump", method = {RequestMethod.POST})
    public ResponseEntity<byte[]> imageBumpByFile(
            @ApiParam(name = "file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name = "edgeAction", required = false, defaultValue = "1") @RequestParam(value = "edgeAction", required = false, defaultValue = "1") int edgeAction
            , @ApiParam(name = "useAlpha", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "useAlpha", required = false, defaultValue = "true") boolean useAlpha
            , @ApiParam(name = "matrix", required = false, defaultValue = "-1.0,-1.0,0.0,-1.0,1.0,1.0,0.0,1.0,1.0") @RequestParam(value = "matrix", required = false, defaultValue = "-1.0,-1.0,0.0,-1.0,1.0,1.0,0.0,1.0,1.0") String matrix
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        // convert string array into float array
        String[] matrixStrings = matrix.split(",");
        float[] matrixValues = new float[matrixStrings.length];
        for (int i = 0; i < matrixStrings.length; i++)
        {
            String s = matrixStrings[i];
            matrixValues[i] = Float.parseFloat(s);
        }

        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.FILE, file);
        args.put("edgeAction", edgeAction);
        args.put("useAlpha", useAlpha);
        args.put("matrix", matrixValues);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageBumpFilterChannel);

        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( bufferedImage, contentType, returnAsBase64 );
        return result;
    }





    @ApiOperation(value = "This filter reduces light noise in an image using the eight hull algorithm described in Applied Optics, Vol. 24, No. 10, 15 May 1985, \"Geometric filter for Speckle Reduction\", by Thomas R Crimmins. Basically, it tries to move each pixel closer in value to its neighbours. As it only has a small effect, you may need to apply it several times. This is good for removing small levels of noise from an image but does give the image some fuzziness.")
    @RequestMapping(value = "/{cacheId}/despeckle", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> imageDespeckleById(
            @ApiParam(name = "cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52") @PathVariable("cacheId") String cacheId
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {

        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.KEY, cacheId);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageDespeckleFilterChannel);

        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( bufferedImage, contentType, returnAsBase64 );
        return result;

    }


    @ApiOperation(value = "This filter reduces light noise in an image using the eight hull algorithm described in Applied Optics, Vol. 24, No. 10, 15 May 1985, \"Geometric filter for Speckle Reduction\", by Thomas R Crimmins. Basically, it tries to move each pixel closer in value to its neighbours. As it only has a small effect, you may need to apply it several times. This is good for removing small levels of noise from an image but does give the image some fuzziness.")
    @RequestMapping(value = "/despeckle", method = {RequestMethod.POST})
    public ResponseEntity<byte[]> imageDespeckleByFile(
            @ApiParam(name = "file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {

        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.FILE, file);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageDespeckleFilterChannel);

        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( bufferedImage, contentType, returnAsBase64 );
        return result;
    }





    @ApiOperation(value = "This filter performs a Gaussian blur on an image.")
    @RequestMapping(value = "/{cacheId}/gaussian", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> imageGaussianById(
            @ApiParam(name = "cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52") @PathVariable("cacheId") String cacheId
            , @ApiParam(name = "radius", required = true, defaultValue = "2") @RequestParam(required = false, defaultValue = "2") int radius
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {

        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.KEY, cacheId);
        args.put("radius", radius);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageGaussianFilterChannel);

        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( bufferedImage, contentType, returnAsBase64 );
        return result;

    }


    @ApiOperation(value = "This filter performs a Gaussian blur on an image.")
    @RequestMapping(value = "/gaussian", method = {RequestMethod.POST})
    public ResponseEntity<byte[]> imageDespeckleByFile(
            @ApiParam(name = "file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name = "radius", required = true, defaultValue = "2") @RequestParam(required = false, defaultValue = "2") int radius
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {

        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.FILE, file);
        args.put("radius", radius);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageGaussianFilterChannel);

        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( bufferedImage, contentType, returnAsBase64 );
        return result;
    }








    @ApiOperation(value = "This filter produces a glowing effect on an image by adding a blurred version of the image to subtracted from the original image.")
    @RequestMapping(value = "/{cacheId}/glow", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> imageGlowById(
            @ApiParam(name = "cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52") @PathVariable("cacheId") String cacheId
            , @ApiParam(name = "amount", required = true, defaultValue = "2") @RequestParam(required = false, defaultValue = "2") int amount
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {

        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.KEY, cacheId);
        args.put("amount", amount);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageGlowFilterChannel);

        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( bufferedImage, contentType, returnAsBase64 );
        return result;

    }


    @ApiOperation(value = "This filter produces a glowing effect on an image by adding a blurred version of the image to subtracted from the original image.")
    @RequestMapping(value = "/glow", method = {RequestMethod.POST})
    public ResponseEntity<byte[]> imageGlowByFile(
            @ApiParam(name = "file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name = "amount", required = true, defaultValue = "2") @RequestParam(required = false, defaultValue = "2") int amount
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {

        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.FILE, file);
        args.put("amount", amount);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageGlowFilterChannel);

        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( bufferedImage, contentType, returnAsBase64 );
        return result;
    }











    @ApiOperation(value = "This filter converts an image to a grayscale image. To do this it finds the brightness of each pixel and sets the red, green and blue of the output to the brightness value. But what is the brightness? The simplest answer might be that it is the average of the RGB components, but that neglects the way in which the human eye works. The eye is much more sensitive to green and red than it is to blue, and so we need to take less acount of the blue and more account of the green. The weighting used by GrayscaleFilter is: luma = 77R + 151G + 28B")
    @RequestMapping(value = "/{cacheId}/grayscale", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> imageGrayscaleById(
            @ApiParam(name = "cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52") @PathVariable("cacheId") String cacheId
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.KEY, cacheId);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageGrayScaleFilterChannel);

        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( bufferedImage, contentType, returnAsBase64 );
        return result;

    }


    @ApiOperation(value = "This filter converts an image to a grayscale image. To do this it finds the brightness of each pixel and sets the red, green and blue of the output to the brightness value. But what is the brightness? The simplest answer might be that it is the average of the RGB components, but that neglects the way in which the human eye works. The eye is much more sensitive to green and red than it is to blue, and so we need to take less acount of the blue and more account of the green. The weighting used by GrayscaleFilter is: luma = 77R + 151G + 28B")
    @RequestMapping(value = "/grayscale", method = {RequestMethod.POST})
    public ResponseEntity<byte[]> imageGrayscaleByFile(
            @ApiParam(name = "file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.FILE, file);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageGrayScaleFilterChannel);

        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( bufferedImage, contentType, returnAsBase64 );
        return result;
    }




    @ApiOperation(value = "This filter simulates the blurring caused by a camera lens. You can change the aperture size and shape and also specify blooming of the image. This filter is very slow.")
    @RequestMapping(value = "/{cacheId}/lensblur", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> imageLensBlurById(
            @ApiParam(name = "cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52") @PathVariable("cacheId") String cacheId
            , @ApiParam(name = "radius", required = false, defaultValue = "10") @RequestParam(value = "Float", required = false, defaultValue = "10") Float radius
            , @ApiParam(name = "sides", required = false, defaultValue = "5") @RequestParam(required = false, defaultValue = "5") Integer sides
            , @ApiParam(name = "bloom", required = false, defaultValue = "2") @RequestParam(required = false, defaultValue = "2") Float bloom
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {

        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.KEY, cacheId);
        args.put("radius", radius);
        args.put("sides", sides);
        args.put("bloom", bloom);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageLensBlurFilterChannel);

        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( bufferedImage, contentType, returnAsBase64 );
        return result;

    }


    @ApiOperation(value = "This filter simulates the blurring caused by a camera lens. You can change the aperture size and shape and also specify blooming of the image. This filter is very slow.")
    @RequestMapping(value = "/lensblur", method = {RequestMethod.POST})
    public ResponseEntity<byte[]> imageLensBlurByFile(
            @ApiParam(name = "file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name = "radius", required = false, defaultValue = "10") @RequestParam(value = "radius", required = false, defaultValue = "10") float radius
            , @ApiParam(name = "sides", required = false, defaultValue = "5") @RequestParam(value = "sides", required = false, defaultValue = "5") int sides
            , @ApiParam(name = "bloom", required = false, defaultValue = "2") @RequestParam(value = "bloom", required = false, defaultValue = "2") float bloom
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.FILE, file);
        args.put("radius", radius);
        args.put("sides", sides);
        args.put("bloom", bloom);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageLensBlurFilterChannel);

        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( bufferedImage, contentType, returnAsBase64 );
        return result;
    }



    /**
     * A filter which performs a box blur on an image. The horizontal and vertical blurs can be specified separately and a number of iterations can be given which allows an approximation to Gaussian blur.
     *
     * @param cacheId
     * @param maskCacheId
     * @param returnAsBase64
     * @return image
     */
    @ApiOperation(value = "This filter blurs an image very slightly using a 3x3 blur kernel. ")
    @RequestMapping(value = "/{cacheId}/mask", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> imageMaskById(
            @ApiParam(name = "cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52")  @PathVariable("cacheId") String cacheId
            , @ApiParam(name = "maskCacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52")  @RequestParam("maskCacheId") String maskCacheId
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map args = new HashMap();
        args.put(ApiServerConstants.HTTP_REQUEST, request);
        args.put(ApiServerConstants.HTTP_RESPONSE, response);
        args.put(ImageConfigMBeanImpl.KEY, cacheId);
        args.put(ImageConfigMBeanImpl.MASK_CACHEID, maskCacheId);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageMaskFilterChannel);

        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( bufferedImage, contentType, returnAsBase64 );
        return result;
    }



    /**
     * This filter blurs an image very slightly using a 3x3 blur kernel.
     *
     * @param file
     * @param maskFile
     * @param returnAsBase64
     * @return image
     */
    @ApiOperation(value = "This filter blurs an image very slightly using a 3x3 blur kernel.")
    @RequestMapping(value = "/mask", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<byte[]> imageMaskByFile(
            @ApiParam(name = "file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name = "maskFile", required = true) @RequestParam MultipartFile maskFile
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map args = new LinkedHashMap();
        args.put(ApiServerConstants.HTTP_REQUEST, request);
        args.put(ApiServerConstants.HTTP_RESPONSE, response);
        args.put(ImageConfigMBeanImpl.FILE, file);
        args.put(ImageConfigMBeanImpl.MASK_FILE, maskFile);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageMaskFilterChannel);


        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler(  bufferedImage, contentType, returnAsBase64);
        return result;
    }


    /**
     * A filter which performs a box blur on an image. The horizontal and vertical blurs can be specified separately and a number of iterations can be given which allows an approximation to Gaussian blur.
     *
     * @param cacheId
     * @param returnAsBase64
     * @return image
     */
    @ApiOperation(value = "This filter replaces each pixel by the maximum of the input pixel and its eight neighbours. Each of the RGB channels is considered separately.")
    @RequestMapping(value = "/{cacheId}/maximum", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> imageMaximumById(
            @ApiParam(name = "cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52")  @PathVariable("cacheId") String cacheId
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map args = new HashMap();
        args.put(ApiServerConstants.HTTP_REQUEST, request);
        args.put(ApiServerConstants.HTTP_RESPONSE, response);
        args.put(ImageConfigMBeanImpl.KEY, cacheId);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageMaximumFilterChannel);

        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( bufferedImage, contentType, returnAsBase64 );
        return result;
    }



    /**
     * This filter blurs an image very slightly using a 3x3 blur kernel.
     *
     * @param file
     * @param returnAsBase64
     * @return image
     */
    @ApiOperation(value = "This filter replaces each pixel by the maximum of the input pixel and its eight neighbours. Each of the RGB channels is considered separately.")
    @RequestMapping(value = "/maximum", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<byte[]> imageMaximumByFile(
            @ApiParam(name = "file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map args = new LinkedHashMap();
        args.put(ApiServerConstants.HTTP_REQUEST, request);
        args.put(ApiServerConstants.HTTP_RESPONSE, response);
        args.put(ImageConfigMBeanImpl.FILE, file);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageMaximumFilterChannel);


        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler(  bufferedImage, contentType, returnAsBase64);
        return result;
    }



    /**
     * This filter replaces each pixel by the median of the input pixel and its eight neighbours. Each of the RGB channels is considered separately.
     *
     * @param cacheId
     * @param returnAsBase64
     * @return image
     */
    @ApiOperation(value = "This filter replaces each pixel by the median of the input pixel and its eight neighbours. Each of the RGB channels is considered separately.")
    @RequestMapping(value = "/{cacheId}/median", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> imageMedianById(
            @ApiParam(name = "cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52")  @PathVariable("cacheId") String cacheId
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map args = new HashMap();
        args.put(ApiServerConstants.HTTP_REQUEST, request);
        args.put(ApiServerConstants.HTTP_RESPONSE, response);
        args.put(ImageConfigMBeanImpl.KEY, cacheId);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageMedianFilterChannel);

        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( bufferedImage, contentType, returnAsBase64 );
        return result;
    }



    /**
     * This filter replaces each pixel by the median of the input pixel and its eight neighbours. Each of the RGB channels is considered separately.
     *
     * @param file
     * @param returnAsBase64
     * @return image
     */
    @ApiOperation(value = "This filter replaces each pixel by the median of the input pixel and its eight neighbours. Each of the RGB channels is considered separately.")
    @RequestMapping(value = "/median", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<byte[]> imageMedianByFile(
            @ApiParam(name = "file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map args = new LinkedHashMap();
        args.put(ApiServerConstants.HTTP_REQUEST, request);
        args.put(ApiServerConstants.HTTP_RESPONSE, response);
        args.put(ImageConfigMBeanImpl.FILE, file);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageMedianFilterChannel);


        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler(  bufferedImage, contentType, returnAsBase64);
        return result;
    }




    /**
     * This filter replaces each pixel by the median of the input pixel and its eight neighbours. Each of the RGB channels is considered separately.
     *
     * @param cacheId
     * @param returnAsBase64
     * @return image
     */
    @ApiOperation(value = "This filter replaces each pixel by the minimum of the input pixel and its eight neighbours. Each of the RGB channels is considered separately.")
    @RequestMapping(value = "/{cacheId}/minimum", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> imageMinimumById(
            @ApiParam(name = "cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52")  @PathVariable("cacheId") String cacheId
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map args = new HashMap();
        args.put(ApiServerConstants.HTTP_REQUEST, request);
        args.put(ApiServerConstants.HTTP_RESPONSE, response);
        args.put(ImageConfigMBeanImpl.KEY, cacheId);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageMinimumFilterChannel);

        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( bufferedImage, contentType, returnAsBase64 );
        return result;
    }



    /**
     * This filter replaces each pixel by the median of the input pixel and its eight neighbours. Each of the RGB channels is considered separately.
     *
     * @param file
     * @param returnAsBase64
     * @return image
     */
    @ApiOperation(value = "This filter replaces each pixel by the minimum of the input pixel and its eight neighbours. Each of the RGB channels is considered separately.")
    @RequestMapping(value = "/minimum", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<byte[]> imageMinimumByFile(
            @ApiParam(name = "file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map args = new LinkedHashMap();
        args.put(ApiServerConstants.HTTP_REQUEST, request);
        args.put(ApiServerConstants.HTTP_RESPONSE, response);
        args.put(ImageConfigMBeanImpl.FILE, file);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageMinimumFilterChannel);


        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler(  bufferedImage, contentType, returnAsBase64);
        return result;
    }





    /**
     * This filter replaces each pixel by the median of the input pixel and its eight neighbours. Each of the RGB channels is considered separately.
     *
     * @param cacheId
     * @param returnAsBase64
     * @return image
     */
    @ApiOperation(value = "This filter simulates motion blur on an image. You specify a combination of angle/distance for linear motion blur, a rotaiton angle for spin blur or a zoom factor for zoom blur. You can combine these in any proportions you want to get effects like spiral blurs.")
    @RequestMapping(value = "/{cacheId}/motionblur", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> imageMotionBlurById(
            @ApiParam(name = "cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52")  @PathVariable("cacheId") String cacheId
            , @ApiParam(name="angle", required = true, defaultValue = "0")  @RequestParam(value="angle", required = false, defaultValue="0") float angle
            , @ApiParam(name="distance", required = true, defaultValue = "1")  @RequestParam(value="distance", required = false,  defaultValue="0") float distance
            , @ApiParam(name="rotation", required = true, defaultValue = "0")  @RequestParam(value="rotation", required = false,  defaultValue="0") float rotation
            , @ApiParam(name="wrapEdges", required = true, defaultValue = "false")  @RequestParam(value="wrapEdges", required = false,  defaultValue="false") boolean wrapEdges
            , @ApiParam(name="zoom", required = true, defaultValue = "0")  @RequestParam(value="zoom", required = false,  defaultValue="0") float zoom
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map args = new HashMap();
        args.put(ApiServerConstants.HTTP_REQUEST, request);
        args.put(ApiServerConstants.HTTP_RESPONSE, response);
        args.put(ImageConfigMBeanImpl.KEY, cacheId);
        args.put("angle", angle);
        args.put("distance", distance);
        args.put("rotation", rotation);
        args.put("wrapEdges", wrapEdges);
        args.put("zoom", zoom);


        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageMotionBlurFilterChannel);

        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( bufferedImage, contentType, returnAsBase64 );
        return result;
    }



    /**
     * This filter replaces each pixel by the median of the input pixel and its eight neighbours. Each of the RGB channels is considered separately.
     *
     * @param file
     * @param returnAsBase64
     * @return image
     */
    @ApiOperation(value = "This filter simulates motion blur on an image. You specify a combination of angle/distance for linear motion blur, a rotaiton angle for spin blur or a zoom factor for zoom blur. You can combine these in any proportions you want to get effects like spiral blurs.")
    @RequestMapping(value = "/motionblur", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<byte[]> imageMotionBlurByFile(
            @ApiParam(name = "file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name="angle", required = true, defaultValue = "0")  @RequestParam(value="angle", required = false, defaultValue="0") float angle
            , @ApiParam(name="distance", required = true, defaultValue = "1")  @RequestParam(value="distance", required = false,  defaultValue="0") float distance
            , @ApiParam(name="rotation", required = true, defaultValue = "0")  @RequestParam(value="rotation", required = false,  defaultValue="0") float rotation
            , @ApiParam(name="wrapEdges", required = true, defaultValue = "false")  @RequestParam(value="wrapEdges", required = false,  defaultValue="false") boolean wrapEdges
            , @ApiParam(name="zoom", required = true, defaultValue = "0")  @RequestParam(value="zoom", required = false,  defaultValue="0") float zoom
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map args = new LinkedHashMap();
        args.put(ApiServerConstants.HTTP_REQUEST, request);
        args.put(ApiServerConstants.HTTP_RESPONSE, response);
        args.put(ImageConfigMBeanImpl.FILE, file);
        args.put("angle", angle);
        args.put("distance", distance);
        args.put("rotation", rotation);
        args.put("wrapEdges", wrapEdges);
        args.put("zoom", zoom);


        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageMotionBlurFilterChannel);


        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler(  bufferedImage, contentType, returnAsBase64);
        return result;
    }




    /**
     * This filter replaces each pixel by the median of the input pixel and its eight neighbours. Each of the RGB channels is considered separately.
     *
     * @param cacheId
     * @param returnAsBase64
     * @return image
     */
    @ApiOperation(value = "This filter produces an oil painting effect as described in the book \"Beyond Photography - The Digital Darkroom\". You can specify the smearing radius. It's quite a slow filter especially with a large radius.")
    @RequestMapping(value = "/{cacheId}/oil", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> imageOilBlurById(
            @ApiParam(name = "cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52")  @PathVariable("cacheId") String cacheId
            , @ApiParam(name="angle", required = true, defaultValue = "3")  @RequestParam(value="angle", required = false, defaultValue="3") int angle
            , @ApiParam(name="range", required = true, defaultValue = "256")  @RequestParam(value="range", required = false,  defaultValue="256") int range
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map args = new HashMap();
        args.put(ApiServerConstants.HTTP_REQUEST, request);
        args.put(ApiServerConstants.HTTP_RESPONSE, response);
        args.put(ImageConfigMBeanImpl.KEY, cacheId);
        args.put("angle", angle);
        args.put("range", range);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageOilFilterChannel);

        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( bufferedImage, contentType, returnAsBase64 );
        return result;
    }



    /**
     * This filter replaces each pixel by the median of the input pixel and its eight neighbours. Each of the RGB channels is considered separately.
     *
     * @param file
     * @param returnAsBase64
     * @return image
     */
    @ApiOperation(value = "This filter produces an oil painting effect as described in the book \"Beyond Photography - The Digital Darkroom\". You can specify the smearing radius. It's quite a slow filter especially with a large radius.")
    @RequestMapping(value = "/oil", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<byte[]> imageOilBlurByFile(
            @ApiParam(name = "file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name="angle", required = true, defaultValue = "3")  @RequestParam(value="angle", required = false, defaultValue="3") int angle
            , @ApiParam(name="range", required = true, defaultValue = "256")  @RequestParam(value="range", required = false,  defaultValue="256") int range
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map args = new LinkedHashMap();
        args.put(ApiServerConstants.HTTP_REQUEST, request);
        args.put(ApiServerConstants.HTTP_RESPONSE, response);
        args.put(ImageConfigMBeanImpl.FILE, file);
        args.put("angle", angle);
        args.put("range", range);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageOilFilterChannel);


        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler(  bufferedImage, contentType, returnAsBase64);
        return result;
    }





    /**
     * This filter simulates light rays coming out of an image. It uses the brightness of the image as the brightness of the rays, but allows you to specify a threshold below which rays won't be emitted which gives a bit more focus to them.
     *
     * @param cacheId
     * @param returnAsBase64
     * @return image
     */
    @ApiOperation(value = "This filter simulates light rays coming out of an image. It uses the brightness of the image as the brightness of the rays, but allows you to specify a threshold below which rays won't be emitted which gives a bit more focus to them.")
    @RequestMapping(value = "/{cacheId}/rays", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> imageRaysBlurById(
            @ApiParam(name = "cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52")  @PathVariable("cacheId") String cacheId
            , @ApiParam(name="opacity", required = true, defaultValue = "o")  @RequestParam(value="opacity", required = false, defaultValue="3") int opacity
            , @ApiParam(name="range", required = true, defaultValue = "256")  @RequestParam(value="range", required = false,  defaultValue="256") int range
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map args = new HashMap();
        args.put(ApiServerConstants.HTTP_REQUEST, request);
        args.put(ApiServerConstants.HTTP_RESPONSE, response);
        args.put(ImageConfigMBeanImpl.KEY, cacheId);
        args.put("opacity", opacity);
        args.put("range", range);

/**  todo
float opacity = ((Float)props.get("levels")).floatValue();
float strength = ((Float)props.get("levels")).floatValue();
float threshold = ((Float)props.get("levels")).floatValue();
float angle = ((Float)props.get("levels")).floatValue();
float centerX = ((Float)props.get("levels")).floatValue();
float centerY = ((Float)props.get("levels")).floatValue();
float distance = ((Float)props.get("levels")).floatValue();
float rotation = ((Float)props.get("levels")).floatValue();
float zoom = ((Float)props.get("levels")).floatValue();
boolean raysOnly = ((Boolean)props.get("range")).booleanValue();

String colorMapType = (String)props.get("colorMapType"); //gradient,grayscale,array,linear,spectrum,spline
int[] gradientColors = (int[])props.get("gradientColors");
int[] xKnots = (int[])props.get("xKnots");
int[] yKnots = (int[])props.get("yKnots");
int[] arrayColors = (int[])props.get("arrayColors");
int linearColor1 = ((Integer)props.get("linearColor1")).intValue();
int linearColor2 = ((Integer)props.get("linearColor2")).intValue();
**/

        ModelAndView view = null;//todo channelInvoker.invokeGenericChannel(request, null, args, imageRaysFilterChannel);

        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler( bufferedImage, contentType, returnAsBase64 );
        return result;
    }



    /**
     * This filter simulates light rays coming out of an image. It uses the brightness of the image as the brightness of the rays, but allows you to specify a threshold below which rays won't be emitted which gives a bit more focus to them.
     *
     * @param file
     * @param returnAsBase64
     * @return image
     */
    @ApiOperation(value = "This filter simulates light rays coming out of an image. It uses the brightness of the image as the brightness of the rays, but allows you to specify a threshold below which rays won't be emitted which gives a bit more focus to them.")
    @RequestMapping(value = "/rays", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<byte[]> imageRaysByFile(
            @ApiParam(name = "file", required = true) @RequestParam MultipartFile file
            , @ApiParam(name="angle", required = true, defaultValue = "3")  @RequestParam(value="angle", required = false, defaultValue="3") int angle
            , @ApiParam(name="range", required = true, defaultValue = "256")  @RequestParam(value="range", required = false,  defaultValue="256") int range
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map args = new LinkedHashMap();
        args.put(ApiServerConstants.HTTP_REQUEST, request);
        args.put(ApiServerConstants.HTTP_RESPONSE, response);
        args.put(ImageConfigMBeanImpl.FILE, file);

/**  todo
float opacity = ((Float)props.get("levels")).floatValue();
float strength = ((Float)props.get("levels")).floatValue();
float threshold = ((Float)props.get("levels")).floatValue();
float angle = ((Float)props.get("levels")).floatValue();
float centerX = ((Float)props.get("levels")).floatValue();
float centerY = ((Float)props.get("levels")).floatValue();
float distance = ((Float)props.get("levels")).floatValue();
float rotation = ((Float)props.get("levels")).floatValue();
float zoom = ((Float)props.get("levels")).floatValue();
boolean raysOnly = ((Boolean)props.get("range")).booleanValue();

String colorMapType = (String)props.get("colorMapType"); //gradient,grayscale,array,linear,spectrum,spline
int[] gradientColors = (int[])props.get("gradientColors");
int[] xKnots = (int[])props.get("xKnots");
int[] yKnots = (int[])props.get("yKnots");
int[] arrayColors = (int[])props.get("arrayColors");
int linearColor1 = ((Integer)props.get("linearColor1")).intValue();
int linearColor2 = ((Integer)props.get("linearColor2")).intValue();
**/



        ModelAndView view = null;//todo  channelInvoker.invokeGenericChannel(request, null, args, imageRaysFilterChannel);


        BufferedImage bufferedImage = (BufferedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        String contentType = (String)view.getModel().get(ImageConfigMBeanImpl.CONTENT_TYPE);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler(  bufferedImage, contentType, returnAsBase64);
        return result;
    }

}
