package unitTests.v1_0.images;

import apiserver.apis.v1_0.images.ImageConfigMBean;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.apis.v1_0.images.gateways.images.ImageMetadataGateway;
import apiserver.apis.v1_0.images.gateways.jobs.images.FileMetadataJob;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * User: mikenimer
 * Date: 8/20/13
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:apiserver-core/src/main/webapp/WEB-INF/config/application-context-test.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/apis-servlet-test.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/flows/image/imageMetadata-flow.xml"})

public class ImageMetadataDrewMetadataExtractorTest
{
    public final Logger log = LoggerFactory.getLogger(ImageMetadataDrewMetadataExtractorTest.class);

    public String hostName = "";
    public int port = 0;

    @Resource(name="supportedMimeTypes")
    public HashMap<String, String> supportedMimeTypes;

    @Autowired
    public ImageMetadataGateway gateway;

    @Autowired
    public ImageConfigMBean config;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;


    @Before
    public void setup() throws Exception
    {
        config.setMetadataLibrary(ImageConfigMBeanImpl.DREW_METADATA_EXTRACTOR);

        System.out.println(config.getMetadataLibrary());
    }



    @Test
    public void testMetadata() throws ServletException, IOException, Exception
    {
        int width = 500;
        int height = 296;
        String fileName = "Apple iPhone 4.jpeg";
        String url = "/rest/v1/image/metadata/info.json";

        File file = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());


        FileMetadataJob model = new FileMetadataJob();
        model.supportedMimeTypes = supportedMimeTypes;
        model.setFile(file);
        Future<Map> future = gateway.getMetadata(model);


        FileMetadataJob result = (FileMetadataJob)future.get(defaultTimeout, TimeUnit.MILLISECONDS);


        ObjectMapper mapper = new ObjectMapper();
        Map metadata = result.getMetadata();

        //Assert.notNull(root.get("coldfusion"));
        Assert.isTrue( metadata.get("Jpeg") != null );
        Assert.isTrue( metadata.get("GPS") != null );
        Assert.isTrue( metadata.get("Exif IFD0") != null );
        Assert.isTrue( metadata.get("ICC Profile") != null );
        Assert.isTrue( metadata.get("Jfif") != null );
        Assert.isTrue( metadata.get("Exif SubIFD") != null );

    }
}
