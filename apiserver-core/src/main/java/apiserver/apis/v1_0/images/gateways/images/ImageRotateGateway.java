package apiserver.apis.v1_0.images.gateways.images;

import apiserver.apis.v1_0.images.models.images.ImageRotateModel;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/21/13
 */
public interface ImageRotateGateway
{
    Future<Map> rotateImage(ImageRotateModel args);
}
