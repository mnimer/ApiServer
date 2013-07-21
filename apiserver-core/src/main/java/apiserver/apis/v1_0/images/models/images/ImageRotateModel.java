package apiserver.apis.v1_0.images.models.images;

import apiserver.apis.v1_0.images.models.ImageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikenimer
 * Date: 7/21/13
 */
public class ImageRotateModel extends ImageModel
{
    public final Logger log = LoggerFactory.getLogger(ImageRotateModel.class);

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
