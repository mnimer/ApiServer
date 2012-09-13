package activator;

import coldfusion.CfmServlet;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * User: mnimer
 * Date: 8/21/12
 */
public class BundleContextListener implements ServletContextListener
{
    private ServiceTracker serviceTracker;
    private BundleContext bc;
    private ServiceRegistration registration;


    public void contextInitialized(ServletContextEvent sce)
    {
        bc = (BundleContext) sce.getServletContext().getAttribute("osgi-bundlecontext");
        serviceTracker = new ServiceTracker(bc, CfmServlet.class.getName(), new Customizer());
        serviceTracker.open();
    }


    public void contextDestroyed(ServletContextEvent sce)
    {
        serviceTracker.close();
        serviceTracker = null;
    }


    private class Customizer implements ServiceTrackerCustomizer
    {

        public Object addingService(ServiceReference reference)
        {
            System.out.println("CFMServlet is linked");

            Dictionary<String, String> props = new Hashtable<String, String>();
            props.put("echo_type", "WAB");
            registration = bc.registerService(CfmServlet.class.getName(), BundleContextListener.this, props);

            return bc.getService(reference);
        }


        public void modifiedService(ServiceReference reference, Object service)
        {
        }


        public void removedService(ServiceReference reference, Object service)
        {
            registration.unregister();
            System.out.println("CFMServlet is unlinked");
        }
    }
}
