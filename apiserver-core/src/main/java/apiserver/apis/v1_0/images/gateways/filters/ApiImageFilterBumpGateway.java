package apiserver.apis.v1_0.images.gateways.filters;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/9/13
 */
public interface ApiImageFilterBumpGateway
{
    Future<Map> imageBumpFilter(String ID, int edgeAction, Boolean useAlpha, float[] matrix, Boolean returnAsBase64);
    Future<Map> imageBumpFilter(File file, int edgeAction, Boolean useAlpha, float[] matrix, Boolean returnAsBase64);
    Future<Map> imageBumpFilter(MultipartFile file, int edgeAction, Boolean useAlpha, float[] matrix, Boolean returnAsBase64);
}
