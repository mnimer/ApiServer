package apiserver.core.connectors.coldfusion;

import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mikenimer
 * Date: 3/24/13
 * Time: 1:50 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IColdFusionBridge
{

    public Object invoke(String cfcPath, String method, Map<String,Object> methodArgs_) throws Throwable;

    public Map<String, Object> extractPropertiesFromPayload(Object props)  throws IOException;

}
