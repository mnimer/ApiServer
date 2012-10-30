package apiserver.apis.v1_0.images;

import apiserver.apis.v1_0.images.wrappers.CachedImage;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.rmi.server.UID;
import java.util.UUID;

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


    public static File getFile( Object uploadedFile ) throws IOException
    {
        FileOutputStream outputStream = null;
        String filePath = System.getProperty("java.io.tmpdir") + "/" + UUID.randomUUID().toString() +"." +fileName(uploadedFile).split("\\.")[1];

        File file = new File(filePath);
        outputStream = new FileOutputStream(file);
        outputStream.write( fileBytes(uploadedFile) );
        outputStream.close();

        return file;


        /**
        byte[] bytes = fileBytes(uploadedFile);
        File file = File.createTempFile( UUID.randomUUID().toString(), "." +fileName(uploadedFile).split("\\.")[1] );

        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        int c = 0;
        while( c < bytes.length )
        {
            out.write( bytes[c] );
            c++;
        }
        out.close();
        return file;
         **/
    }



}
