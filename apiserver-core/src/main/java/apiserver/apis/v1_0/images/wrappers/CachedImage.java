package apiserver.apis.v1_0.images.wrappers;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * User: mnimer
 * Date: 9/28/12
 */
public class CachedImage
{
    private String fileName;
    private byte[] fileBytes;


    public CachedImage(File file) throws IOException
    {
        if( !file.exists() || file.isDirectory() )
        {
            throw new IOException("Invalid File Reference");
        }

        fileName = file.getName();

        byte[] bytes = FileUtils.readFileToByteArray(file);
        this.fileBytes = bytes;
    }

    public CachedImage(CommonsMultipartFile file) throws IOException
    {
        fileName = file.getOriginalFilename();
        this.fileBytes = file.getBytes();
    }


    public String getFileName()
    {
        return fileName;
    }


    public byte[] getFileBytes()
    {
        return fileBytes;
    }
}
