package apiserver.core.connectors.jcr;


import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.jackrabbit.core.TransientRepository;

import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

/**
 * Created with IntelliJ IDEA.
 * User: mikenimer
 * Date: 3/22/13
 * Time: 10:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class JcrBridge
{


    private Repository repository;


    public JcrBridge() throws Exception
    {
        //todo: initialize client workspaces
    }


    public Repository getRepository()
    {
        return repository;
    }


    public void setRepository(Repository repository_)
    {
        this.repository = repository_;
    }


    /**
     *
     * @throws Exception
     */
    private void validate() throws Exception
    {
        Session session = null;
        try
        {
            Repository repository = this.repository;//new TransientRepository();
            SimpleCredentials creds = new SimpleCredentials("admin", "admin".toCharArray());
            session = repository.login(creds);
            System.out.println("Workspace: " + session.getWorkspace().getName() + "\n");
        }
        finally
        {
            if( session != null )
            {
                session.logout();
            }
        }
    }

}
