package apiserver.apis.v1_0.images.gateways.filters;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/10/13
 */
public interface ApiImageFilterLensBlurGateway
{
    Future<Map> imageLensBlurFilter(String ID, Float radius, Integer sides, Float bloom);
    Future<Map> imageLensBlurFilter(File file, Float radius, Integer sides, Float bloom);
    Future<Map> imageLensBlurFilter(MultipartFile file, Float radius, Integer sides, Float bloom);
}
