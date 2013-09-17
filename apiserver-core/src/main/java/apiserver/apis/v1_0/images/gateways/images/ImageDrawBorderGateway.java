package apiserver.apis.v1_0.images.gateways.images;

import apiserver.apis.v1_0.images.models.ImageModel;
import apiserver.apis.v1_0.images.models.images.ImageBorderModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 9/16/13
 */
public interface ImageDrawBorderGateway
{
    Future<Map> imageDrawBorderFilter(ImageBorderModel imageModel);
}
