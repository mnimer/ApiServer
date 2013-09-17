package apiserver.apis.v1_0.images.controllers;

import apiserver.apis.v1_0.common.ResponseEntityHelper;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.apis.v1_0.images.gateways.images.ImageDrawBorderGateway;
import apiserver.apis.v1_0.images.gateways.images.ImageDrawTextGateway;
import apiserver.apis.v1_0.images.models.filters.BoxBlurModel;
import apiserver.apis.v1_0.images.models.images.ImageBorderModel;
import apiserver.apis.v1_0.images.models.images.ImageTextModel;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.MessageChannel;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * User: mnimer
 * Date: 9/18/12
 */
//Controller
@RequestMapping("/image-draw")
public class ImageDrawingController
{
    @Autowired
    private ImageDrawBorderGateway imageDrawBorderGateway;

    @Autowired
    private ImageDrawTextGateway imageDrawTextGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;


    public MessageChannel imageDrawBorderInputChannel;

    public MessageChannel imageDrawTextInputChannel;


    /**
     * Draw a border around a cached image
     * @param cacheId
     * @param color
     * @param thickness
     * @param returnAsBase64
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws IOException
     */
    @RequestMapping(value = "/{cacheId}/border", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> drawBorderById(
            @ApiParam(name="cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52") @PathVariable(value = "cacheId") String cacheId
            , @ApiParam(name="color", required = true) @RequestParam(required = true) String color
            , @ApiParam(name="thickness", required = true) @RequestParam(required = true) Integer thickness
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        ImageBorderModel args = new ImageBorderModel();
        args.setCacheId(cacheId);
        args.setColor(color);
        args.setThickness(thickness);

        Future<Map> imageFuture = imageDrawBorderGateway.imageDrawBorderFilter(args);
        ImageBorderModel payload = (ImageBorderModel)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getProcessedFile();
        String contentType = payload.getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, returnAsBase64);
        return result;
    }


    /**
     * Draw a border around an image
     * @param file
     * @param color
     * @param thickness
     * @param returnAsBase64
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws IOException
     */
    @RequestMapping(value = "/border", method = {RequestMethod.POST})
    public ResponseEntity<byte[]> drawBorderByImage(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file
            , @ApiParam(name="color", required = true) @RequestParam(required = true) String color
            , @ApiParam(name="thickness", required = true) @RequestParam(required = true) Integer thickness
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        ImageBorderModel args = new ImageBorderModel();
        args.setFile(file);
        args.setColor(color);
        args.setThickness(thickness);

        Future<Map> imageFuture = imageDrawBorderGateway.imageDrawBorderFilter(args);
        ImageBorderModel payload = (ImageBorderModel)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getProcessedFile();
        String contentType = payload.getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, returnAsBase64);
        return result;
    }


    /**
     * Overlay text onto a cached image
     * @param cacheId
     * @param text
     * @param color
     * @param fontSize
     * @param fontStyle
     * @param angle
     * @param x
     * @param y
     * @param returnAsBase64
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws IOException
     */
    @RequestMapping(value = "/{cacheId}/watermark", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> drawTextByImage(
            @ApiParam(name="cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52") @PathVariable(value = "cacheId") String cacheId
            , @ApiParam(name="text", required = true) @RequestParam(required = true) String text
            , @ApiParam(name="color", required = true) @RequestParam(required = true) String color
            , @ApiParam(name="fontSize", required = true) @RequestParam(required = true) String fontSize
            , @ApiParam(name="fontStyle", required = true) @RequestParam(required = true) String fontStyle
            , @ApiParam(name="angle", required = true) @RequestParam(required = true) Integer angle
            , @ApiParam(name="x", required = true) @RequestParam(required = true) Integer x
            , @ApiParam(name="y", required = true) @RequestParam(required = true) Integer y
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        ImageTextModel args = new ImageTextModel();
        args.setCacheId(cacheId);
        args.setText(text);
        args.setColor(color);
        args.setFontSize(fontSize);
        args.setFontStyle(fontStyle);
        args.setAngle(angle);
        args.setX(x);
        args.setY(y);

        Future<Map> imageFuture = imageDrawTextGateway.imageDrawTextFilter(args);
        ImageTextModel payload = (ImageTextModel)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getProcessedFile();
        String contentType = payload.getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, returnAsBase64);
        return result;

    }


    /**
     * Overlay text onto an image
     *
     * @param file
     * @param text
     * @param color
     * @param fontSize
     * @param fontStyle
     * @param angle
     * @param x
     * @param y
     * @param returnAsBase64
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws IOException
     */
    @RequestMapping(value = "/watermark", method = {RequestMethod.POST})
    public ResponseEntity<byte[]> drawTextByImage(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file
            , @ApiParam(name="text", required = true) @RequestParam(required = true) String text
            , @ApiParam(name="color", required = true) @RequestParam(required = true) String color
            , @ApiParam(name="fontSize", required = true) @RequestParam(required = true) String fontSize
            , @ApiParam(name="fontStyle", required = true) @RequestParam(required = true) String fontStyle
            , @ApiParam(name="angle", required = true) @RequestParam(required = true) Integer angle
            , @ApiParam(name="x", required = true) @RequestParam(required = true) Integer x
            , @ApiParam(name="y", required = true) @RequestParam(required = true) Integer y
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        ImageTextModel args = new ImageTextModel();
        args.setFile(file);
        args.setText(text);
        args.setColor(color);
        args.setFontSize(fontSize);
        args.setFontStyle(fontStyle);
        args.setAngle(angle);
        args.setX(x);
        args.setY(y);

        Future<Map> imageFuture = imageDrawTextGateway.imageDrawTextFilter(args);
        ImageTextModel payload = (ImageTextModel)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getProcessedFile();
        String contentType = payload.getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, returnAsBase64);
        return result;

    }
}
