package apiserver.apis.v1_0.images.models.cache;

import apiserver.apis.v1_0.images.models.ImageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikenimer
 * Date: 7/19/13
 */
public class CacheGetModel extends ImageModel
{
    public final Logger log = LoggerFactory.getLogger(CacheGetModel.class);

    private String cacheId;
    private Boolean returnAsBase64;


    public String getCacheId()
    {
        return cacheId;
    }


    public void setCacheId(String cacheId)
    {
        this.cacheId = cacheId;
    }


    public Boolean getReturnAsBase64()
    {
        return returnAsBase64;
    }


    public void setReturnAsBase64(Boolean returnAsBase64)
    {
        this.returnAsBase64 = returnAsBase64;
    }
}
