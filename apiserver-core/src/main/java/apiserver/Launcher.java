package apiserver;

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

/**
 *
 * This class launches the web application in an embedded Jetty container.
 * This is the entry point to your application. The Java command that is used for
 * launching should fire this main method.
 *
 */
public class Launcher {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception{

    }

    /**
    public static void main(String[] args) throws Exception{
        String webappDirLocation = "src/main/webapp/";
        String warDirLocation = "src/main/wars/";

        //The port that we should run on can be set into an environment variable
        //Look for that variable and default to 9000 if it isn't there.
        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "9000";
        }

        Server server = new Server(Integer.valueOf(webPort));


        WebAppContext root = new WebAppContext();
        root.setContextPath("/");
        root.setDescriptor(webappDirLocation+"/WEB-INF/web.xml");
        root.setResourceBase(webappDirLocation);


        //WebAppContext war1 = new WebAppContext();
        //war1.setContextPath("/ping");
        //war1.setWar(warDirLocation +"module-ping-1.0-SNAPSHOT.war");
        //war1.setExtractWAR(true);


        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[] { root }); // , war1 });
        server.setHandler(contexts);


        //Parent loader priority is a class loader setting that Jetty accepts.
        //By default Jetty will behave like most web containers in that it will
        //allow your application to replace non-server libraries that are part of the
        //container. Setting parent loader priority to true changes this behavior.
        //Read more here: http://wiki.eclipse.org/Jetty/Reference/Jetty_Classloading
        root.setParentLoaderPriority(true);

        //HandlerList handlers = new HandlerList();
        //handlers.setHandlers(new Handler[] { root, war1, new DefaultHandler() });
        //server.setHandler(root);

        server.start();
        server.join();
    }
     **/


    /**
     * Full configuration, instead of using default jetty.xml files
     * @see http://www.eclipse.org/jetty/documentation/current/embedding-jetty.html
     */
    /**
     public static void main(String[] args) throws Exception {
     String jetty_home = System.getProperty("jetty.home", "../../jetty-distribution/target/distribution");
     System.setProperty("jetty.home", jetty_home);
     QueuedThreadPool threadPool = new QueuedThreadPool();
     threadPool.setMaxThreads(500);
     Server server = new Server(threadPool);
     server.addBean(new ScheduledExecutorScheduler());
     HttpConfiguration http_config = new HttpConfiguration();
     http_config.setSecureScheme("https");
     http_config.setSecurePort(8443);
     http_config.setOutputBufferSize(32768);
     http_config.setRequestHeaderSize(8192);
     http_config.setResponseHeaderSize(8192);
     http_config.setSendServerVersion(true);
     http_config.setSendDateHeader(false);
     HandlerCollection handlers = new HandlerCollection();
     ContextHandlerCollection contexts = new ContextHandlerCollection();
     handlers.setHandlers(new Handler[] { contexts, new DefaultHandler() });
     server.setHandler(handlers);
     server.setDumpAfterStart(false);
     server.setDumpBeforeStop(false);
     server.setStopAtShutdown(true);
     MBeanContainer mbContainer = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
     server.addBean(mbContainer);
     ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(http_config));
     http.setPort(8080);
     http.setIdleTimeout(30000);
     server.addConnector(http);
     SslContextFactory sslContextFactory = new SslContextFactory();
     sslContextFactory.setKeyStorePath(jetty_home + "/etc/keystore");
     sslContextFactory.setKeyStorePassword("OBF:1vny1zlo1x8e1vnw1vn61x8g1zlu1vn4");
     sslContextFactory.setKeyManagerPassword("OBF:1u2u1wml1z7s1z7a1wnl1u2g");
     sslContextFactory.setTrustStorePath(jetty_home + "/etc/keystore");
     sslContextFactory.setTrustStorePassword("OBF:1vny1zlo1x8e1vnw1vn61x8g1zlu1vn4");
     sslContextFactory.setExcludeCipherSuites("SSL_RSA_WITH_DES_CBC_SHA", "SSL_DHE_RSA_WITH_DES_CBC_SHA", "SSL_DHE_DSS_WITH_DES_CBC_SHA", "SSL_RSA_EXPORT_WITH_RC4_40_MD5", "SSL_RSA_EXPORT_WITH_DES40_CBC_SHA", "SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA", "SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA");
     HttpConfiguration https_config = new HttpConfiguration(http_config);
     https_config.addCustomizer(new SecureRequestCustomizer());
     ServerConnector sslConnector = new ServerConnector(server, new SslConnectionFactory(sslContextFactory, "http/1.1"), new HttpConnectionFactory(https_config));
     sslConnector.setPort(8443);
     server.addConnector(sslConnector);
     DeploymentManager deployer = new DeploymentManager();
     deployer.setContexts(contexts);
     deployer.setContextAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*  /servlet-api-[^/] *  \\.jar$"); // note to self: remove spaces in regex
     WebAppProvider webapp_provider = new WebAppProvider();
     webapp_provider.setMonitoredDirName(jetty_home + "/webapps");
     webapp_provider.setDefaultsDescriptor(jetty_home + "/etc/webdefault.xml");
     webapp_provider.setScanInterval(1);
     webapp_provider.setExtractWars(true);
     webapp_provider.setConfigurationManager(new PropertiesConfigurationManager());
     deployer.addAppProvider(webapp_provider);
     server.addBean(deployer);
     StatisticsHandler stats = new StatisticsHandler();
     stats.setHandler(server.getHandler());
     server.setHandler(stats);
     NCSARequestLog requestLog = new NCSARequestLog();
     requestLog.setFilename(jetty_home + "/logs/yyyy_mm_dd.request.log");
     requestLog.setFilenameDateFormat("yyyy_MM_dd");
     requestLog.setRetainDays(90);
     requestLog.setAppend(true);
     requestLog.setExtended(true);
     requestLog.setLogCookies(false);
     requestLog.setLogTimeZone("GMT");
     RequestLogHandler requestLogHandler = new RequestLogHandler();
     requestLogHandler.setRequestLog(requestLog);
     handlers.addHandler(requestLogHandler);
     LowResourceMonitor lowResourcesMonitor = new LowResourceMonitor(server);
     lowResourcesMonitor.setPeriod(1000);
     lowResourcesMonitor.setLowResourcesIdleTimeout(200);
     lowResourcesMonitor.setMonitorThreads(true);
     lowResourcesMonitor.setMaxConnections(0);
     lowResourcesMonitor.setMaxMemory(0);
     lowResourcesMonitor.setMaxLowResourcesTime(5000);
     server.addBean(lowResourcesMonitor);
     HashLoginService login = new HashLoginService();
     login.setName("Test Realm");
     login.setConfig(jetty_home + "/etc/realm.properties");
     login.setRefreshInterval(0);
     server.addBean(login);
     server.start();
     server.join();
     }
     **/

}
