package apiserver.apis.v1_0.images.gateways.jobs.filters;

import apiserver.apis.v1_0.images.gateways.jobs.ImageDocumentJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikenimer
 * Date: 7/14/13
 */
public class OilJob extends ImageDocumentJob
{
    private final Logger log = LoggerFactory.getLogger(OilJob.class);

    private int levels;
    private int range;


    public int getLevels()
    {
        return levels;
    }


    public void setLevels(int levels)
    {
        this.levels = levels;
    }


    public int getRange()
    {
        return range;
    }


    public void setRange(int range)
    {
        this.range = range;
    }
}
