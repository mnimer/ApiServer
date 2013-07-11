package apiserver.apis.v1_0.images.gateways.filters;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/10/13
 */
public interface ApiImageFilterMinimumGateway
{
    Future<Map> imageMinimumFilter(String ID);
    Future<Map> imageMinimumFilter(File file);
    Future<Map> imageMinimumFilter(MultipartFile file);
}
