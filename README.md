API-OSGi-Server
===============


SETUP
================
1. Download an OSGI server, I've been using Apache Karaf
http://karaf.apache.org/

2. install the "webconsole" and "war" features (see Karaf docs)


3. In the service.coldfusion POM is a reference to a local cfusion.jar, you need to follow the directions and add this to your local maven repository. The cfusion.jar is in the /resource/cfusion.war  file