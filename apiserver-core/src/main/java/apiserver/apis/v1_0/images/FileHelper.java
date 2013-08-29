package apiserver.apis.v1_0.images;

import apiserver.apis.v1_0.images.wrappers.CachedImage;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * User: mnimer
 * Date: 9/29/12
 */
public class FileHelper
{

    public static String fileName ( Object uploadedFile )
    {
        if( uploadedFile instanceof CommonsMultipartFile)
        {
            return ((CommonsMultipartFile)uploadedFile).getOriginalFilename();
        }
        else if( uploadedFile instanceof CachedImage)
        {
            return ((CachedImage)uploadedFile).getFileName();
        }
        else if( uploadedFile instanceof BufferedImage)
        {
            return null;
        }

        return null;
    }


    public static byte[] fileBytes ( Object uploadedFile ) throws IOException
    {

        if( uploadedFile instanceof CommonsMultipartFile)
        {
            return ((CommonsMultipartFile)uploadedFile).getBytes();
        }
        else if( uploadedFile instanceof CachedImage)
        {
            return ((CachedImage)uploadedFile).getFileBytes();
        }
        else if( uploadedFile instanceof BufferedImage)
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(  (BufferedImage)uploadedFile, "jpg", baos);
            return baos.toByteArray();
        }

        return null;
    }





}
