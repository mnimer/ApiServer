package apiserver.apis.v1_0.images.gateways.images;

import apiserver.apis.v1_0.images.gateways.jobs.images.FileInfoJob;
import apiserver.apis.v1_0.images.model.ImageInfo;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/5/13
 */
public interface ImageInfoGateway
{
    Future<Map> imageInfo(FileInfoJob args);
}
