package apiserver.apis.v1_0.status.gateways;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 6/14/13
 */
public interface ApiStatusGateway
{
    Future<Map> checkApiServerAsync();
    Map checkApiServerSync();

}