package apiserver.core.common;

import com.adobe.xmp.impl.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * User: mikenimer
 * Date: 7/7/13
 */
public class ResponseEntityHelper
{
    public final Logger log = LoggerFactory.getLogger(ResponseEntityHelper.class);


    /**
     * return a BufferedImage as byte[] array or as a base64 version of the image bytes
     *
     * @param image
     * @param contentType
     * @param returnAsBase64
     * @return
     * @throws java.io.IOException
     */
    public static ResponseEntity<byte[]> processImage(Object image, String contentType, Boolean returnAsBase64) throws IOException
    {
        // set content type
        String convertToType = "jpg";

        if (contentType == null)
        {
            contentType = "image/jpg";
            contentType = contentType.toLowerCase();
        }


        if (image instanceof BufferedImage)
        {
            //DataBufferByte bytes = (DataBufferByte)((BufferedImage) image).getRaster().getDataBuffer();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write((BufferedImage) image, convertToType, baos);
            byte[] bytes = baos.toByteArray();

            return processFile(bytes, contentType, returnAsBase64);

        } else if (image instanceof byte[])
        {
            return processFile((byte[]) image, contentType, returnAsBase64);
        }

        throw new RuntimeException("Invalid Image bytes");
    }


    /**
     * return a BufferedImage as byte[] array or as a base64 version of the image bytes
     *
     * @param image
     * @param contentType
     * @param returnAsBase64
     * @return
     * @throws java.io.IOException
     */
    public static ResponseEntity<byte[]> processFile(byte[] bytes, String contentType, Boolean returnAsBase64) throws IOException
    {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.parseMediaType(contentType)); //todo verify this is right.

        if (bytes instanceof byte[])
        {
            if (!returnAsBase64)
            {
                return new ResponseEntity<byte[]>((byte[]) bytes, headers, HttpStatus.OK);
            } else
            {
                return new ResponseEntity<byte[]>(Base64.encode((byte[]) bytes), headers, HttpStatus.OK);
            }
        }

        throw new RuntimeException("Invalid bytes");
    }


    /**
     * For simple requests, return any object (maps, strings, etc.)
     *
     * @return
     * @throws java.io.IOException
     */
    public static ResponseEntity<Object> processObject(Object object) throws IOException
    {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Object>(object, headers, HttpStatus.OK);
    }
}
