package apiserver.apis.v1_0.images.gateways.images;

import apiserver.apis.v1_0.images.models.images.FileBorderModel;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 9/16/13
 */
public interface ImageDrawBorderGateway
{
    Future<Map> imageDrawBorderFilter(FileBorderModel imageModel);
}
