package apiserver.apis.v1_0.images.gateways.filters;

import apiserver.core.models.FileModel;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/10/13
 */
public interface ApiImageFilterMinimumGateway
{
    Future<Map> imageMinimumFilter(FileModel fileModel);
}
