package apiserver.apis.v1_0.images.models.filters;

import apiserver.apis.v1_0.images.models.ImageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikenimer
 * Date: 7/14/13
 */
public class GaussianModel extends ImageModel
{
    public final Logger log = LoggerFactory.getLogger(GaussianModel.class);

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
