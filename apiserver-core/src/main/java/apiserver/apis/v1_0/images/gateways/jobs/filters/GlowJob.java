package apiserver.apis.v1_0.images.gateways.jobs.filters;

import apiserver.apis.v1_0.images.gateways.jobs.ImageDocumentJob;
import apiserver.core.models.FileModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikenimer
 * Date: 7/14/13
 */
public class GlowJob extends ImageDocumentJob
{
    public final Logger log = LoggerFactory.getLogger(GlowJob.class);

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
