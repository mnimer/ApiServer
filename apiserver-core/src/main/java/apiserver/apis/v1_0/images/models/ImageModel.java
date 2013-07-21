package apiserver.apis.v1_0.images.models;

import apiserver.apis.v1_0.images.wrappers.CachedImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * User: mikenimer
 * Date: 7/13/13
 */
public class ImageModel
{
    public final Logger log = LoggerFactory.getLogger(ImageModel.class);

    private String cacheId = null;
    private File file = null;
    private MultipartFile multipartFile = null;
    private CachedImage cachedImage = null;
    private BufferedImage processedImage = null;
    private Integer timeToLiveInSeconds = 0; // no cache


    public String getCacheId()
    {
        return cacheId;
    }


    public void setCacheId(String cacheId)
    {
        this.cacheId = cacheId;
    }


    public File getFile()
    {
        return file;
    }


    public void setFile(File file)
    {
        this.file = file;
    }


    public MultipartFile getMultipartFile()
    {
        return multipartFile;
    }


    public void setMultipartFile(MultipartFile multipartFile)
    {
        this.multipartFile = multipartFile;
    }


    public CachedImage getCachedImage()
    {
        return cachedImage;
    }


    public void setCachedImage(CachedImage cachedImage)
    {
        this.cachedImage = cachedImage;
    }


    public BufferedImage getProcessedImage()
    {
        return processedImage;
    }


    public void setProcessedImage(BufferedImage processedImage)
    {
        this.processedImage = processedImage;
    }


    public Integer getTimeToLiveInSeconds()
    {
        return timeToLiveInSeconds;
    }


    public void setTimeToLiveInSeconds(Integer timeToLiveInSeconds)
    {
        this.timeToLiveInSeconds = timeToLiveInSeconds;
    }
}

