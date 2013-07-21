package apiserver.apis.v1_0.images.gateways.images;

import apiserver.apis.v1_0.images.models.images.GetCaptchaModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/20/13
 */
public interface GetCaptchaGateway
{
    Future<Map> getCaptcha(GetCaptchaModel args);
}
