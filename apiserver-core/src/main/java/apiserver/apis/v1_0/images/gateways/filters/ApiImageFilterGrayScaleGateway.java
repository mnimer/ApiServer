package apiserver.apis.v1_0.images.gateways.filters;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/10/13
 */
public interface ApiImageFilterGrayScaleGateway
{
    Future<Map> imageGrayScaleFilter(String ID);
    Future<Map> imageGrayScaleFilter(File file);
    Future<Map> imageGrayScaleFilter(MultipartFile file);
}
