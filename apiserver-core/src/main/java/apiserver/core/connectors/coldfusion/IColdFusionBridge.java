package apiserver.core.connectors.coldfusion;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: mikenimer
 * Date: 3/24/13
 * Time: 1:50 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IColdFusionBridge
{

    public Object invoke(String cfcPath, String method, Object[] methodArgs_, HttpServletRequest request) throws Throwable;
}
