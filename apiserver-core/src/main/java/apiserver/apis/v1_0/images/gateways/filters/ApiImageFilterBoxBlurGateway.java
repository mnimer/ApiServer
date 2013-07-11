package apiserver.apis.v1_0.images.gateways.filters;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/9/13
 */
public interface ApiImageFilterBoxBlurGateway
{
    Future<Map> imageBoxBlurFilter(String ID, int hRadius, int vRadius, int iterations, boolean preMultiplyAlpha, Boolean returnAsBase64);
    Future<Map> imageBoxBlurFilter(File file, int hRadius, int vRadius, int iterations, boolean preMultiplyAlpha, Boolean returnAsBase64);
    Future<Map> imageBoxBlurFilter(MultipartFile file, int hRadius, int vRadius, int iterations, boolean preMultiplyAlpha, Boolean returnAsBase64);
}
