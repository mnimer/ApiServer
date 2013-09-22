package apiserver.core.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * User: mikenimer
 * Date: 7/13/13
 */
@Component
public class FileModel
{
    public final Logger log = LoggerFactory.getLogger(FileModel.class);

    @Resource(name="supportedMimeTypes")
    public HashMap<String, String> supportedMimeTypes;


    private Object file = null;
    private String contentType = null;
    private byte[] processedFileBytes = null;
    private String base64File = null;
    private Integer timeToLiveInSeconds = 0; // no cache

    private BufferedImage bufferedImage;



    public String getContentType()
    {
        return contentType;
    }


    public File getFile() throws IOException
    {
        if( file instanceof MultipartFile )
        {
            String[] nameParts = ((MultipartFile)file).getName().split(".");
            String contentType = supportedMimeTypes.get(nameParts[1]);
            File newFile = File.createTempFile( nameParts[0], contentType );
            ((MultipartFile)file).transferTo(newFile);
            return newFile;
        }
        else if( file instanceof File )
        {
            return (File)file;
        }

        return null;
    }


    /**
     *
     * @param file instance of File, MultiPartFile, CachedFile
     */
    public void setFile(Object file)
    {
        this.file = file;

        if( file instanceof  File)
        {
            String extension = ((File)file).getName().split("\\.")[1];
            String mimeType = null;//(String)supportedMimeTypes.get( extension );   //todo
            contentType = supportedMimeTypes.get(extension);
            if( mimeType == null )
            {
                contentType = "application/octet-stream";//catch all
            }
        }
        else if( file instanceof  MultipartFile)
        {
            contentType = ((MultipartFile) file).getContentType();
        }
    }


    public byte[] getProcessedFileBytes()
    {
        return processedFileBytes;
    }


    public void setProcessedFileBytes(byte[] getProcessedFileBytes)
    {
        this.processedFileBytes = getProcessedFileBytes;
    }


    public void setProcessedFileBytes(BufferedImage image)
    {
        this.processedFileBytes = ((DataBufferByte)image.getData().getDataBuffer()).getData();
    }

    public void setProcessedFileBytes(File _file)
    {
        try
        {
            byte[] fileData = Files.readAllBytes(Paths.get(_file.getPath()));
            this.processedFileBytes = fileData;
        }catch( IOException ioe){
            log.error(ioe.getMessage(), ioe);
        }
    }


    public Integer getTimeToLiveInSeconds()
    {
        return timeToLiveInSeconds;
    }


    public void setTimeToLiveInSeconds(Integer timeToLiveInSeconds)
    {
        this.timeToLiveInSeconds = timeToLiveInSeconds;
    }


    public String getBase64File()
    {
        return base64File;
    }


    public void setBase64File(String base64File)
    {
        this.base64File = base64File;
    }


    public byte[] getFileBytes()
    {
        try
        {
            if( file != null )
            {
                if( file instanceof File  )
                {
                    byte[] fileData = Files.readAllBytes(Paths.get(((File)file).getPath()));
                    return fileData;
                }
                else if( file instanceof MultipartFile )
                {
                    return ((MultipartFile)file).getBytes();
                }
            }
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }


    public BufferedImage getBufferedImage() throws IOException
    {
        if( bufferedImage != null )
        {
            return bufferedImage;
        }
        bufferedImage = ImageIO.read(new ByteArrayInputStream(getFileBytes()));
        return bufferedImage;
    }

}

