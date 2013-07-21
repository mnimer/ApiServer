package apiserver.apis.v1_0.images.gateways.filters;

import apiserver.apis.v1_0.images.models.filters.MaskModel;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/10/13
 */
public interface ApiImageFilterMaskGateway
{
    Future<Map> imageMaskFilter(MaskModel maskFilterModel);
}
