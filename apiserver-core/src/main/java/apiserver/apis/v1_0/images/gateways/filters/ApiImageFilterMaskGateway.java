package apiserver.apis.v1_0.images.gateways.filters;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/10/13
 */
public interface ApiImageFilterMaskGateway
{
    Future<Map> imageMaskFilter(String ID, String MaskCacheId);
    Future<Map> imageMaskFilter(File file, File mask);
    Future<Map> imageMaskFilter(MultipartFile file, MultipartFile mask);
}
