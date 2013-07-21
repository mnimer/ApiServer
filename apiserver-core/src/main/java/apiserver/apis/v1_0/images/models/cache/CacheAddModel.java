package apiserver.apis.v1_0.images.models.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * User: mikenimer
 * Date: 7/19/13
 */
public class CacheAddModel
{
    public final Logger log = LoggerFactory.getLogger(CacheAddModel.class);

    private File file;
    private MultipartFile multipartFile;
    private Integer timeToLiveInSeconds;


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


    public Integer getTimeToLiveInSeconds()
    {
        return timeToLiveInSeconds;
    }


    public void setTimeToLiveInSeconds(Integer timeToLiveInSeconds)
    {
        this.timeToLiveInSeconds = timeToLiveInSeconds;
    }
}
