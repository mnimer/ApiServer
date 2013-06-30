package com.mikenimer.apiserver;

import coldfusion.cfc.CFCProxy;
import coldfusion.runtime.CFPage;
import coldfusion.runtime.JSONUtils;
import com.oreilly.servlet.MultipartRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mikenimer
 * Date: 6/9/13
 */
public class HttpBridgeServlet extends HttpServlet
{
    public final Logger log = LoggerFactory.getLogger(HttpBridgeServlet.class);

    HashMap cfcPathCache = new HashMap();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        // Parse the request
        try
        {
            MultipartRequest multipartRequest = new MultipartRequest(request, System.getProperty("java.io.tmpdir"));
            String _cfcPath = multipartRequest.getParameter("cfcPath");
            String _cfcMethod = multipartRequest.getParameter("cfcMethod");
            String[] _cfcArguments = null;


            if( multipartRequest.getParameter("cfcArguments") != null )
            {
                _cfcArguments = multipartRequest.getParameter("cfcArguments").split("&");
            }else{
                _cfcArguments = new String[0];
            }



            int argumentIndex = 0;
            Enumeration names = multipartRequest.getParameterNames();
            Object[] methodArgs = new Object[_cfcArguments.length];//(String[])httpServletRequest.getParameterValues("methodArgs");
            for (String argument : _cfcArguments)
            {
                String _key = null;
                String _default = null;
                if( argument.indexOf(':') > -1 ) // has default
                {
                    _key = argument.split(":")[0];
                    _default = argument.split(":")[1];
                }
                else
                {
                    _key = argument;
                }


                String val = multipartRequest.getParameter(_key);
                if( val == null )
                {
                    val = _default;
                }

                methodArgs[argumentIndex++] = val;
            }




            String cfcPath = (String)cfcPathCache.get(_cfcPath);

            if( cfcPath == null )
            {
                cfcPath = request.getRealPath(_cfcPath);
                cfcPathCache.put(_cfcPath, cfcPath);
            }



            CFCProxy myCFC = new CFCProxy(cfcPath, false);
            Object cfcResult = myCFC.invoke(_cfcMethod, methodArgs);


            //Map cfData = new HashMap();
            //cfData.put("result", serialize(cfcResult));


            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write( serialize(cfcResult) );
        }
        catch (Throwable ex)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }


    /**
     * Convert the ColdFusion specific object returned from a CFC into a JSON String
     * @param result
     * @return
     */
    protected String serialize(Object result)
    {
        return JSONUtils.serializeJSON(result);
    }
}
