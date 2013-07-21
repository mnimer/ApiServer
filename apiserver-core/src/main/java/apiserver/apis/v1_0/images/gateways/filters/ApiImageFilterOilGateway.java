package apiserver.apis.v1_0.images.gateways.filters;

import apiserver.apis.v1_0.images.models.filters.OilModel;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/10/13
 */
public interface ApiImageFilterOilGateway
{
    Future<Map> imageOilFilter(OilModel oilFilterModel);
}
