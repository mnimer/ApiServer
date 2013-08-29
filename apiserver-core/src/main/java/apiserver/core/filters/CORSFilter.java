package apiserver.core.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: mnimer
 * Date: 10/24/12
 */
public class CORSFilter implements Filter
{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        //do nothing
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        chain.doFilter(request, response);


        // write back to response
        // todo: this is temporary, eventually we'll cross check token and request limits and block
        ((HttpServletResponse)response).addHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse)response).addHeader("Access-Control-Allow-Credentials", "true");

    }


    @Override
    public void destroy()
    {
        //do nothing
    }
}
