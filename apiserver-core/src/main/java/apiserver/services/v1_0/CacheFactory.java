package apiserver.services.v1_0;

import net.sf.ehcache.CacheManager;

/**
 * User: mnimer
 * Date: 9/18/12
 */
public class CacheFactory
{
    private static CacheManager cacheManager;

    public static CacheManager getCacheManager()
    {
        // todo add support for other cache libraries
        // todo add Cache Configuration support (via MBean)
        if( cacheManager == null )
        {
            cacheManager = CacheManager.newInstance();
        }

        return cacheManager;
    }

}
