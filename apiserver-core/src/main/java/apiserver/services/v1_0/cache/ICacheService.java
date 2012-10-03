package apiserver.services.v1_0.cache;

/**
 * User: mnimer
 * Date: 8/17/12
 *
 * A cache interface that could be implemented by a system like ehcache, or memcache - then exposed to applications through this interface.
 */
public interface ICacheService
{
    /**
     *
     * @param obj
     * @param timeoutInSeconds -1, cache forever (with no guarentee it will be forever)
     * @return  key
     */
    String add(Object obj, int timeoutInSeconds);

    /**
     * Get an item out of the cache, based on KEY returned from add() method
     * @param key
     * @return  object in cache or NULL
     */
    Object getCache(String key);
}
