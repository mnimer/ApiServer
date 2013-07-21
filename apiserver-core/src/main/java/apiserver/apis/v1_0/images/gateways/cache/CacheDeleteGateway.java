package apiserver.apis.v1_0.images.gateways.cache;

import apiserver.apis.v1_0.images.models.cache.CacheDeleteModel;

import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/19/13
 */
public interface CacheDeleteGateway
{
    Future<Boolean> deleteFromCache(CacheDeleteModel args);
}
