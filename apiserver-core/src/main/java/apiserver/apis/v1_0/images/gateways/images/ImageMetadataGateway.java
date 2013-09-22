package apiserver.apis.v1_0.images.gateways.images;

import apiserver.core.models.FileModel;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/21/13
 */
public interface ImageMetadataGateway
{
    Future<Map> getMetadata(FileModel args);

}
