package apiserver.core.servlets;

import org.apache.jackrabbit.servlet.ServletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
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

        try
        {
            Repository repository = new ServletRepository(this); // "this" is the containing servlet
            out.println("JCR SERVLET STATUS: SUCCESS - " +repository.login().getRootNode().toString() );
        }catch(Exception ex){
            out.println("JCR SERVLET STATUS: FAILED");
        }



        try
        {

            InitialContext ctx = new InitialContext();
            Context env = (Context) ctx.lookup("java:comp/env");
            Repository repo = (Repository) env.lookup("jcr/repository");
            Session session = repo.login(new SimpleCredentials("admin", "admin".toCharArray()));

            out.println("JCR JNDI STATUS: SUCCESS - " +session.getRootNode().toString() );
        }catch(Exception ex){
            out.println("JCR JNDI STATUS: FAILED");
        }

        out.close();
    }
}
