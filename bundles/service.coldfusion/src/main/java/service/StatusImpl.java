package service;

import coldfusion.cfc.CFCProxy;
import coldfusion.runtime.Struct;
import library.service.interfaces.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.Map;

/**
 * User: mnimer
 * Date: 8/17/12
 */
public class StatusImpl implements library.service.interfaces.Status
{

    public Map ping(HttpServletRequest request)
    {
        URL location = coldfusion.runtime.NeoPageContext.class.getProtectionDomain().getCodeSource().getLocation();
        System.out.print(location); //todo, use real logging


        Struct cfcResult = null;
        String cfcPath = request.getRealPath("/demos-inf/components/" + "PingExample.cfc"); //cache this lookup for performance

        long start = System.currentTimeMillis();
        try
        {
            CFCProxy myCFC = new CFCProxy(cfcPath, false);
            Object[] myArgs = {};
            cfcResult = (Struct)myCFC.invoke("ping", myArgs);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        long end = System.currentTimeMillis();

        return cfcResult;
    }
}
