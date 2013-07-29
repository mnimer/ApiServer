package apiserver.apis.v1_0.images.models;

import apiserver.apis.v1_0.images.wrappers.CachedImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Properties;

/**
 * User: mikenimer
 * Date: 7/13/13
 */
@Component
public class ImageModel
{
    public final Logger log = LoggerFactory.getLogger(ImageModel.class);

    @Resource(name="supportedMimeTypes")
    public HashMap<String, String> supportedMimeTypes;

    @Resource(name="supportedMimeTypes2")
    public HashMap<String, String> supportedMimeTypes2;


    private String cacheId = null;
    private Object file = null;
    private String contentType = null;
    private BufferedImage processedFile = null;
    private Integer timeToLiveInSeconds = 0; // no cache


    public String getCacheId()
    {
        return cacheId;
    }


    public void setCacheId(String cacheId)
    {
        this.cacheId = cacheId;
    }


    public String getContentType()
    {
        return contentType;
    }


    public Object getFile() throws IOException
    {
        if( file instanceof MultipartFile )
        {
            String[] nameParts = ((MultipartFile)file).getName().split(".");
            File newFile = File.createTempFile( nameParts[0], "image/" +nameParts[1] );
            ((MultipartFile)file).transferTo(newFile);
            return newFile;
        }
        else if( file instanceof CachedImage )
        {
            return ((CachedImage)file).getFile();
        }
        return file;
    }


    /**
     *
     * @param file instance of File, MultiPartFile, CachedImage
     */
    public void setFile(Object file)
    {
        this.file = file;

        if( file instanceof  File)
        {
            String extension = ((File)file).getName().split("\\.")[1];
            String mimeType = null;//(String)supportedMimeTypes.get( extension );   //todo
            contentType = "image/" +extension;
            if( mimeType == null )
            {
                contentType = "application/octet-stream";//catch all
            }
        }
        else if( file instanceof  MultipartFile)
        {
            contentType = ((MultipartFile) file).getContentType();
        }
        else if( file instanceof  CachedImage)
        {
            contentType = ((CachedImage) file).getContentType();
        }
    }


    public CachedImage getCachedImage() throws Exception
    {
        if( file == null || !(file instanceof CachedImage) )
        {
            throw new Exception("File is NULL or Not a CachedImage");
        }

        return (CachedImage)file;
    }



    public BufferedImage getProcessedFile()
    {
        return processedFile;
    }


    public void setProcessedFile(BufferedImage processedFile)
    {
        this.processedFile = processedFile;
    }


    public Integer getTimeToLiveInSeconds()
    {
        return timeToLiveInSeconds;
    }


    public void setTimeToLiveInSeconds(Integer timeToLiveInSeconds)
    {
        this.timeToLiveInSeconds = timeToLiveInSeconds;
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
                else if( file instanceof CachedImage )
                {
                    return ((CachedImage)file).getFileBytes();
                }
            }
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }
}

