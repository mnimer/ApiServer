package apiserver.apis.v1_0.images.models.filters;

import apiserver.apis.v1_0.images.models.ImageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikenimer
 * Date: 7/14/13
 */
public class GlowModel extends ImageModel
{
    public final Logger log = LoggerFactory.getLogger(GlowModel.class);

    private int amount;


    public int getAmount()
    {
        return amount;
    }


    public void setAmount(int amount)
    {
        this.amount = amount;
    }
}
