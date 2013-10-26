package apiserver.core.providers.cache;

import apiserver.apis.v1_0.documents.model.Document;

/**
 * User: mnimer
 * Date: 8/17/12
 *
 * A cache interface that could be implemented by a system like ehcache, or memcache - then exposed to applications through this interface.
 */
public interface IDocumentCacheProvider
{
    /**
     *
     * @param obj
     * @return  key
     */
    Document add(Document obj);

    /**
     * Get an item out of the cache, based on KEY returned from add() method
     * @param key
     * @return  object in cache or NULL
     */
    Document get(String key);

    /**
     * Get an item out of the cache, based on KEY returned from add() method
     * @param key
     * @return  object in cache or NULL
     */
    Document delete(String key);
}
