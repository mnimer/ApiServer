package apiserver.apis.v1_0.images.gateways.cache;

import apiserver.apis.v1_0.images.models.cache.CacheGetModel;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/19/13
 */
public interface CacheGetGateway
{
    Future<Map> getFromCache(CacheGetModel args);
}
