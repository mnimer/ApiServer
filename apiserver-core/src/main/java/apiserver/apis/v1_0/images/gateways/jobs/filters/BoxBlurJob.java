package apiserver.apis.v1_0.images.gateways.jobs.filters;

import apiserver.apis.v1_0.documents.DocumentJob;
import apiserver.apis.v1_0.images.gateways.jobs.ImageDocumentJob;
import apiserver.core.models.FileModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikenimer
 * Date: 7/14/13
 */
public class BoxBlurJob extends ImageDocumentJob
{
    public final Logger log = LoggerFactory.getLogger(BoxBlurJob.class);

    private int hRadius;
    private int vRadius;
    private int iterations;
    private Boolean preMultiplyAlpha;


    public int getHRadius()
    {
        return hRadius;
    }


    public void setHRadius(int hRadius)
    {
        this.hRadius = hRadius;
    }


    public int getVRadius()
    {
        return vRadius;
    }


    public void setVRadius(int vRadius)
    {
        this.vRadius = vRadius;
    }


    public int getIterations()
    {
        return iterations;
    }


    public void setIterations(int iterations)
    {
        this.iterations = iterations;
    }


    public Boolean getPreMultiplyAlpha()
    {
        return preMultiplyAlpha;
    }


    public void setPreMultiplyAlpha(Boolean preMultiplyAlpha)
    {
        this.preMultiplyAlpha = preMultiplyAlpha;
    }
}
