package apiserver.core.cache;

import apiserver.exceptions.FactoryException;

import javax.cache.CacheManager;
import javax.cache.Caching;


/**
 * User: mnimer
 * Date: 9/18/12
 */
public class CacheFactory
{
    private static CacheManager jCacheManager;
    private static net.sf.ehcache.CacheManager ehCacheService;

    public static Object getCacheManager(String library) throws FactoryException
    {
        // todo add support for other cache libraries
        // todo add Cache Configuration support (via MBean)

        if( library == CacheServiceMBean.EHCACHE )
        {
            ehCacheService = net.sf.ehcache.CacheManager.getInstance();
        }
        else if( library == CacheServiceMBean.JCACHE )
        {
            if( jCacheManager == null )
            {
                jCacheManager = Caching.getCacheManager(Thread.currentThread().getContextClassLoader());
            }
            return jCacheManager;
        }


        throw new FactoryException("cache '" +library +"' does not exist");
    }

}
