package apiserver.core.common;

/*******************************************************************************
 Copyright (c) 2013 Mike Nimer.

 This file is part of ApiServer Project.

 The ApiServer Project is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The ApiServer Project is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with the ApiServer Project.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

import org.apache.commons.codec.binary.Base64;
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

    /**
     * return a BufferedImage as byte[] array or as a base64 version of the image bytes
     * @param image
     * @param contentType
     * @param returnAsBase64
     * @return
     * @throws java.io.IOException

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
     */



    public static ResponseEntity<byte[]> processImage(Object image, String contentType, Boolean returnAsBase64) throws IOException
    {
        // set content type
        String convertToType = "png";

        if (contentType == null)
        {
            contentType = "application/octet-stream";
            contentType = contentType.toLowerCase();
        }

        //
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));


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
                return new ResponseEntity<byte[]>(Base64.encodeBase64((byte[]) bytes), headers, HttpStatus.OK);
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
