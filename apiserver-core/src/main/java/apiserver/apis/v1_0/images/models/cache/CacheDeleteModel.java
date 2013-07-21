package apiserver.apis.v1_0.images.models.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikenimer
 * Date: 7/19/13
 */
public class CacheDeleteModel
{
    public final Logger log = LoggerFactory.getLogger(CacheDeleteModel.class);

    private String cacheId;


    public String getCacheId()
    {
        return cacheId;
    }


    public void setCacheId(String cacheId)
    {
        this.cacheId = cacheId;
    }
}
