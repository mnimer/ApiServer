package apiserver.core.providers.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikenimer
 * Date: 10/26/13
 */
public class EhCacheProvider
{
    private final Logger log = LoggerFactory.getLogger(EhCacheProvider.class);


    public EhCacheProvider()
    {
        //Cache _cache = new Cache(new CacheConfiguration(getCacheName(), 1000).eternal(true).memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.CLOCK));
        //_cache.initialise();

    }
}