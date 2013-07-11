package apiserver.apis.v1_0.images.gateways.filters;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/10/13
 */
public interface ApiImageFilterOilGateway
{
    Future<Map> imageOilFilter(String ID, int angle, int range);
    Future<Map> imageOilFilter(File file, int angle, int range);
    Future<Map> imageOilFilter(MultipartFile file, int angle, int range);
}
