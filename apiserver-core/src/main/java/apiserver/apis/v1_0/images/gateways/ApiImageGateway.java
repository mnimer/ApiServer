package apiserver.apis.v1_0.images.gateways;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/5/13
 */
public interface ApiImageGateway
{
    Future<Map> imageInfo(Map args);
}
