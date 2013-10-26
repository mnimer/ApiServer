package apiserver.apis.v1_0.images.gateways.jobs.filters;

import apiserver.apis.v1_0.images.gateways.jobs.ImageDocumentJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikenimer
 * Date: 7/14/13
 */
public class MotionBlurJob extends ImageDocumentJob
{
    private final Logger log = LoggerFactory.getLogger(MotionBlurJob.class);

    private float angle;
    private float distance;
    private float rotation;
    private boolean wrapEdges;
    private float zoom;


    public float getAngle()
    {
        return angle;
    }


    public void setAngle(float angle)
    {
        this.angle = angle;
    }


    public float getDistance()
    {
        return distance;
    }


    public void setDistance(float distance)
    {
        this.distance = distance;
    }


    public float getRotation()
    {
        return rotation;
    }


    public void setRotation(float rotation)
    {
        this.rotation = rotation;
    }


    public boolean isWrapEdges()
    {
        return wrapEdges;
    }


    public void setWrapEdges(boolean wrapEdges)
    {
        this.wrapEdges = wrapEdges;
    }


    public float getZoom()
    {
        return zoom;
    }


    public void setZoom(float zoom)
    {
        this.zoom = zoom;
    }
}
