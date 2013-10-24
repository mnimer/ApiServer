package apiserver.apis.v1_0.images.gateways.jobs.filters;

import apiserver.apis.v1_0.images.gateways.jobs.ImageDocumentJob;
import apiserver.core.models.FileModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikenimer
 * Date: 7/14/13
 */
public class GaussianJob extends ImageDocumentJob
{
    public final Logger log = LoggerFactory.getLogger(GaussianJob.class);

    private int radius;


    public int getRadius()
    {
        return radius;
    }


    public void setRadius(int radius)
    {
        this.radius = radius;
    }
}
