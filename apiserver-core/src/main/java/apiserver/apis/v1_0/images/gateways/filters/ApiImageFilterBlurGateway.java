package apiserver.apis.v1_0.images.gateways.filters;

import org.springframework.integration.annotation.Gateway;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.Controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/7/13
 */

public interface ApiImageFilterBlurGateway
{
    Future<Map> imageBlurFilter(String ID);
    Future<Map> imageBlurFilter(File file);
    Future<Map> imageBlurFilter(MultipartFile file);
}
