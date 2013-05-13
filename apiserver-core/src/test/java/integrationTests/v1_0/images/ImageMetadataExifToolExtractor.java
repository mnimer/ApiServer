package integrationTests.v1_0.images;

import apiserver.apis.v1_0.images.ImageConfigMBean;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import integrationTests.v1_0.HttpTest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

/**
 * User: mnimer
 * Date: 10/19/12
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:**/config/v1_0/apis-servlet-integration-tests.xml"})
@Profile("dev")
@Category(categories.ColdFusionTests.class)
public class ImageMetadataExifToolExtractor extends HttpTest
{
    private String hostName = "localhost";
    private int port = 8100;


    @Before
    public void setup() throws Exception
    {
        /*
        String serviceURL = "service:jmx:rmi:///jndi/rmi://" + hostName + ":" + port +"/jmxrmi";
        JMXServiceURL jmxUrl = new JMXServiceURL(serviceURL);
        JMXConnector jmxCon = JMXConnectorFactory.connect(jmxUrl);

        MBeanServerConnection catalogServerConnection = jmxCon.getMBeanServerConnection();

        Set jmsSet = catalogServerConnection.queryNames(new ObjectName("com.apiserver:name=ImageApi"), null);
        ObjectName imageApiConfig = (ObjectName) jmsSet.iterator().next();

        ImageConfigMBean imageConfigProxy = JMX.newMBeanProxy(catalogServerConnection, imageApiConfig, ImageConfigMBean.class, true);

        imageConfigProxy.setMetadataLibrary(ImageConfigMBeanImpl.EXIFTOOL_METADATA_EXTRACTOR);

        System.out.println(imageConfigProxy.getMetadataLibrary());

        jmxCon.close();
        */
    }


    @Test
    public void testMetadata() throws ServletException, IOException, Exception
    {
        int width = 500;
        int height = 296;
        String fileName = "Apple iPhone 4.jpeg";
        String url = "/rest/v1/image/metadata/info.json";

        File file = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());


        int status = 0;
        // Here we go!
        HttpResponse response = invokeHttpPost(url, file, null);
        status = response.getStatusLine().getStatusCode();


        Assert.isTrue(status == HttpStatus.SC_OK, response.getStatusLine().toString());

        BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(in);

        //Assert.notNull(root.get("coldfusion"));
        Assert.isTrue(((JsonNode) root.get("ExifIFD")) != null);
        Assert.isTrue(((JsonNode) root.get("ICC_Profile")) != null);
        Assert.isTrue(((JsonNode) root.get("JFIF")) != null);
        Assert.isTrue(((JsonNode) root.get("IFD0")) != null);
        Assert.isTrue(((JsonNode) root.get("ICC-view")) != null);
        Assert.isTrue(((JsonNode) root.get("ICC-header")) != null);
        Assert.isTrue(((JsonNode) root.get("File")) != null);
        Assert.isTrue(((JsonNode) root.get("GPS")) != null);
        Assert.isTrue(((JsonNode) root.get("System")) != null);
        Assert.isTrue(((JsonNode) root.get("ICC-meas")) != null);
        Assert.isTrue(((JsonNode) root.get("Composite")) != null);
        Assert.isTrue(((JsonNode) root.get("ExifTool")) != null);


        in.close();

    }
}
