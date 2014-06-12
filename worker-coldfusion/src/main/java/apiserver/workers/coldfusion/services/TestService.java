package apiserver.workers.coldfusion.services;

import apiserver.workers.coldfusion.GridManager;
import coldfusion.cfc.CFCProxy;

import java.lang.reflect.Method;
import java.net.URL;

/**
 * Created by mnimer on 6/8/14.
 */
public class TestService
{
    public String execute(String msg)
    {
        String cfcPath = GridManager.rootPath +"/apiserver-inf/components/v1/api-test.cfc";
        try
        {
            CFCProxy proxy = new CFCProxy(cfcPath, false);
            String result = (String)proxy.invoke("echo", new Object[]{msg});
            System.out.println("execute Result:" +result);
            return result;
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }
        return null;
    }



    public String executeProxy()
    {
        String cfcPath = "/Applications/ColdFusion10/cfusion/wwwroot/apiserver-inf/components/v1/api-test.cfc";
        try
        {
            System.out.println("creating cfc:" +cfcPath);

            Class clazz = Class.forName("coldfusion.cfc.CFCProxy");
            Object cfcProxy = clazz.getDeclaredConstructor(String.class, boolean.class).newInstance(cfcPath, false);

            System.out.println("class created");

            Method echoMethod = cfcProxy.getClass().getMethod("invoke", String.class, Object[].class);

            System.out.println("found method");

            Object[] cfcArgs = new Object[]{ "Hello World"};
            Object[] invokeArgs = new Object[]{ "echo", cfcArgs };
            String result = (String)echoMethod.invoke(cfcProxy, "echo", cfcArgs);

            System.out.println("executeProxy Result:" +result);
            return result;
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }
        return null;
    }
}
