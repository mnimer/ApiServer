package apiserver.apis.v1_0.images;

import apiserver.apis.v1_0.images.wrappers.CachedImage;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

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
            return ((CommonsMultipartFile)uploadedFile).getName();
        }
        else if( uploadedFile instanceof CachedImage)
        {
            return ((CachedImage)uploadedFile).getFileName();
        }

        return null;
    }


    public static byte[] fileBytes ( Object uploadedFile )
    {

        if( uploadedFile instanceof CommonsMultipartFile)
        {
            return ((CommonsMultipartFile)uploadedFile).getBytes();
        }
        else if( uploadedFile instanceof CachedImage)
        {
            return ((CachedImage)uploadedFile).getFileBytes();
        }

        return null;
    }


}
