package apiserver.apis.v1_0.images;

import apiserver.exceptions.FactoryException;
import net.sf.ehcache.Cache;

/**
 * User: mnimer
 * Date: 10/19/12
 */
public interface ImageConfigMBean
{
    public Cache getCache() throws FactoryException;

    public String getCacheName();
    public void setCacheName(String cacheName);

    public String getCacheLibrary();
    public void setCacheLibrary(String cacheLibrary);

    public String getMetadataLibrary();
    public void setMetadataLibrary(String metadataLibrary);
}
