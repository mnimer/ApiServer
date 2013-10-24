package apiserver.apis.v1_0.images.gateways.filters;

import apiserver.apis.v1_0.images.gateways.jobs.filters.BoxBlurJob;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/9/13
 */
public interface ApiImageFilterBoxBlurGateway
{
    Future<Map> imageBoxBlurFilter(BoxBlurJob boxBlurFilterModel);
}
