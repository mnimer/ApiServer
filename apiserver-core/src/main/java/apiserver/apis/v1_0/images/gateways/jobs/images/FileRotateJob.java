package apiserver.apis.v1_0.images.gateways.jobs.images;

import apiserver.apis.v1_0.images.gateways.jobs.ImageDocumentJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikenimer
 * Date: 7/21/13
 */
public class FileRotateJob extends ImageDocumentJob
{
    public final Logger log = LoggerFactory.getLogger(FileRotateJob.class);

    private Integer angle = 0;


    public Integer getAngle()
    {
        return angle;
    }


    public void setAngle(Integer angle)
    {
        this.angle = angle;
    }
}
