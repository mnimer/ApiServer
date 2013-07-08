package apiserver.core.gateways;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/7/13
 */
public interface ApiStatusColdFusionGateway
{
    Future<Map> checkColdfusionAsync();
    Map checkColdfusionSync();
}
