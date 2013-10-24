package apiserver.apis.v1_0.images.gateways.filters;

import apiserver.apis.v1_0.images.gateways.jobs.ImageDocumentJob;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/7/13
 */

public interface ApiImageFilterBlurGateway
{
    Future<Map> imageBlurFilter(ImageDocumentJob jobs);
}
