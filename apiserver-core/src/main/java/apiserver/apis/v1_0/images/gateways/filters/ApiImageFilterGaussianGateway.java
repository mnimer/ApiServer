package apiserver.apis.v1_0.images.gateways.filters;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/10/13
 */
public interface ApiImageFilterGaussianGateway
{
    Future<Map> imageGaussianFilter(String ID, int radius);
    Future<Map> imageGaussianFilter(File file, int radius);
    Future<Map> imageGaussianFilter(MultipartFile file, int radius);
}