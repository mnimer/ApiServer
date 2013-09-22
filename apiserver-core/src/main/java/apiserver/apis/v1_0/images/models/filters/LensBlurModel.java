package apiserver.apis.v1_0.images.models.filters;

import apiserver.core.models.FileModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikenimer
 * Date: 7/14/13
 */
public class LensBlurModel extends FileModel
{
    public final Logger log = LoggerFactory.getLogger(LensBlurModel.class);

    private float radius;
    private int sides;
    private float bloom;


    public float getRadius()
    {
        return radius;
    }


    public void setRadius(float radius)
    {
        this.radius = radius;
    }


    public int getSides()
    {
        return sides;
    }


    public void setSides(int sides)
    {
        this.sides = sides;
    }


    public float getBloom()
    {
        return bloom;
    }


    public void setBloom(float bloom)
    {
        this.bloom = bloom;
    }
}
