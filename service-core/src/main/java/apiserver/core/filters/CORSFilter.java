package apiserver.core.filters;

/*******************************************************************************
 Copyright (c) 2013 Mike Nimer.

 This file is part of ApiServer Project.

 The ApiServer Project is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The ApiServer Project is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with the ApiServer Project.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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
