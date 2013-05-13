package apiserver.core.connectors.coldfusion;

import apiserver.exceptions.MessageConfigException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 *
 * User: mikenimer
 * Date: 3/24/13
 */
public class ColdFusionBridge implements IColdFusionBridge
{
    HashMap cfcPathCache = new HashMap();

    public Object invoke(String cfcPath_, String method_, Object[] myArgs_, HttpServletRequest request_) throws Throwable
    {
        String cfcPath = (String)cfcPathCache.get(cfcPath_);
        if( cfcPath == null )
        {
            if( request_ == null )
            {
                throw new MessageConfigException(MessageConfigException.MISSING_REQUEST_PROPERTY);
            }
            //java 1.7
            //cfcPath = request_.getServletContext().getRealPath(cfcPath_);
            cfcPath = request_.getRealPath(cfcPath_);

        }

        //CFCProxy myCFC = new CFCProxy(cfcPath, false);
        if( myArgs_ == null )
        {
            myArgs_ = new Object[]{};
        }
        return null;// myCFC.invoke(method_, myArgs_);
    }
}
