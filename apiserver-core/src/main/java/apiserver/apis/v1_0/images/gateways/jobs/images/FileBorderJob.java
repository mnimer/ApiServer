package apiserver.apis.v1_0.images.gateways.jobs.images;

import apiserver.apis.v1_0.images.gateways.jobs.ImageDocumentJob;
import apiserver.core.models.FileModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikenimer
 * Date: 9/16/13
 */
public class FileBorderJob extends ImageDocumentJob
{
    public final Logger log = LoggerFactory.getLogger(FileBorderJob.class);

    private String color;
    private Integer thickness;


    public String getColor()
    {
        return color;
    }


    public void setColor(String color)
    {
        this.color = color;
    }


    public Integer getThickness()
    {
        return thickness;
    }


    public void setThickness(Integer thickness)
    {
        this.thickness = thickness;
    }
}
