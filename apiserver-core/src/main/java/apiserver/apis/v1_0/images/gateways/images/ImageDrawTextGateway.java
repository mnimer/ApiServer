package apiserver.apis.v1_0.images.gateways.images;

import apiserver.core.models.FileModel;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 9/16/13
 */
public interface ImageDrawTextGateway
{
    Future<Map> imageDrawTextFilter(FileModel fileModel);
}