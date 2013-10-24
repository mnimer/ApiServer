package apiserver.apis.v1_0.images.gateways.jobs;

import apiserver.apis.v1_0.documents.DocumentJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * User: mikenimer
 * Date: 10/24/13
 */
public class ImageDocumentJob extends DocumentJob
{
    private final Logger log = LoggerFactory.getLogger(ImageDocumentJob.class);

    private String contentType;
    private BufferedImage bufferedImage;
    private Map<String, String> supportedMimeTypes;


    public String getContentType()
    {
        return contentType;
    }


    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }


    public Map<String, String> getSupportedMimeTypes()
    {
        return supportedMimeTypes;
    }


    public void setSupportedMimeTypes(Map<String, String> supportedMimeTypes)
    {
        this.supportedMimeTypes = supportedMimeTypes;
    }


    public BufferedImage getBufferedImage()
    {
        return bufferedImage;
    }


    public void setBufferedImage(BufferedImage bufferedImage)
    {
        this.bufferedImage = bufferedImage;
    }
}
