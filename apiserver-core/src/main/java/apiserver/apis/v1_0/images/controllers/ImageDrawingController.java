package apiserver.apis.v1_0.images.controllers;

import apiserver.apis.v1_0.images.gateways.images.ImageDrawBorderGateway;
import apiserver.apis.v1_0.images.gateways.images.ImageDrawTextGateway;
import apiserver.apis.v1_0.images.models.images.FileBorderModel;
import apiserver.apis.v1_0.images.models.images.FileTextModel;
import apiserver.core.common.ResponseEntityHelper;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.MessageChannel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
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
@RequestMapping("/image/draw")
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
        FileBorderModel args = new FileBorderModel();
        args.setFile(file);
        args.setColor(color);
        args.setThickness(thickness);

        Future<Map> imageFuture = imageDrawBorderGateway.imageDrawBorderFilter(args);
        FileBorderModel payload = (FileBorderModel)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getBufferedImage();
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
    @RequestMapping(value = "/text", method = {RequestMethod.POST})
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
        FileTextModel args = new FileTextModel();
        args.setFile(file);
        args.setText(text);
        args.setColor(color);
        args.setFontSize(fontSize);
        args.setFontStyle(fontStyle);
        args.setAngle(angle);
        args.setX(x);
        args.setY(y);

        Future<Map> imageFuture = imageDrawTextGateway.imageDrawTextFilter(args);
        FileTextModel payload = (FileTextModel)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getBufferedImage();
        String contentType = payload.getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, returnAsBase64);
        return result;

    }
}
