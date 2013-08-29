package unitTests.v1_0.images;

import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.apis.v1_0.images.gateways.images.ImageInfoGateway;
import apiserver.apis.v1_0.images.models.images.ImageInfoModel;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * User: mikenimer
 * Date: 7/6/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:apiserver-core/src/main/webapp/WEB-INF/config/application-context-test.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/apis-servlet-test.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/flows/image/imageInfo-flow.xml"})
public class ImageInfoTest
{
    public final Logger log = LoggerFactory.getLogger(ImageInfoTest.class);

    @Autowired
    private ImageInfoGateway gateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;


    int width = 500;
    int height = 296;
    static String fileName = "IMG_5932_sm.png";
    static String url = "/rest/v1/image/info/size.json";
    static File file = null;
    static Map<String, Object> args = null;


    @BeforeClass
    public static void beforeClass()
    {
        try
        {
            file = new File(ImageInfoTest.class.getClassLoader().getSystemResource(fileName).toURI());

            args = new HashMap();
            args.put(ImageConfigMBeanImpl.FILE, file);


        }catch (Exception ex){
            Assert.fail(ex.getMessage());
        }
    }


    @Test
    public void testImageInfo()
    {
        try
        {
            ImageInfoModel args = new ImageInfoModel();
            args.setFile(file);

            Future<Map> resultFuture = gateway.imageInfo(args);
            Object result = resultFuture.get( defaultTimeout, TimeUnit.MILLISECONDS );

            Assert.assertTrue( result != null );
            Assert.assertTrue( result instanceof Map );
            Assert.assertEquals(width, ((Map)result).get("width"));
            Assert.assertEquals(height, ((Map) result).get("height"));
        }
        catch (Exception ex){
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }
}
