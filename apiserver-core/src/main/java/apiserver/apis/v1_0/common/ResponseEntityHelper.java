package apiserver.apis.v1_0.common;

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
     * @param image
     * @param contentType
     * @param returnAsBase64
     * @return
     * @throws java.io.IOException
     */
    public static ResponseEntity<byte[]> processImage(Object image, String contentType, Boolean returnAsBase64) throws IOException
    {
        HttpHeaders headers = new HttpHeaders();

        // set content type
        String convertToType = "jpg";

        if(contentType == null )
        {
            contentType = "jpg";
            contentType = contentType.toLowerCase();
        }


        if( contentType.contains("jpg") || contentType.contains("jpeg"))
        {
            convertToType = "jpg";
            headers.setContentType(MediaType.IMAGE_JPEG);
        }
        else if( contentType.contains("png"))
        {
            convertToType = "png";
            headers.setContentType(MediaType.IMAGE_PNG);
        }
        else if( contentType.contains("gif"))
        {
            convertToType = "gif";
            headers.setContentType(MediaType.IMAGE_GIF);
        }
        else
        {
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        }



        if( image instanceof BufferedImage)
        {
            //DataBufferByte bytes = (DataBufferByte)((BufferedImage) image).getRaster().getDataBuffer();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write((BufferedImage) image, convertToType, baos);
            byte [] bytes = baos.toByteArray();


            if (!returnAsBase64)
            {
                return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<byte[]>(Base64.encode(bytes) , headers, HttpStatus.OK);
            }
        }
        else if(  image instanceof byte[]  )
        {
            if (!returnAsBase64)
            {
                return new ResponseEntity<byte[]>( (byte[])image, headers, HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<byte[]>(Base64.encode((byte[])image) , headers, HttpStatus.OK);
            }
        }

        throw new RuntimeException("Invalid Image bytes");
    }
}
