package apiserver.apis.v1_0.images.gateways.images;

import apiserver.apis.v1_0.images.models.images.ImageInfoModel;
import org.springframework.integration.annotation.Payload;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/5/13
 */
public interface ImageInfoGateway
{
    Future<Map> imageInfo(ImageInfoModel args);
}
