package apiserver.apis.v1_0.images.gateways.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/10/13
 */
public interface ApiImageFilterMotionBlurGateway
{
    Future<Map> imageMotionBlurFilter(String ID, float angle, float distance, float rotation, boolean wrapEdges, float zoom);
    Future<Map> imageMotionBlurFilter(File file, float angle, float distance, float rotation, boolean wrapEdges, float zoom);
    Future<Map> imageMotionBlurFilter(MultipartFile file, float angle, float distance, float rotation, boolean wrapEdges, float zoom);
}
