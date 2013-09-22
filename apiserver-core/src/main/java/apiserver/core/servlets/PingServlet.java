package apiserver.core.servlets;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * A simple debug servlet, not tied to any API version
 * User: mikenimer
 * Date: 5/13/13
 */
public class PingServlet extends HttpServlet
{
    public final Logger log = LoggerFactory.getLogger(PingServlet.class);



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {

        PrintWriter out = resp.getWriter();
        out.println("ping servlet: " +new Date().toGMTString());

        out.close();
    }
}
