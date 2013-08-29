package unitTests.v1_0.images;

import apiserver.apis.v1_0.images.gateways.images.ImageRotateGateway;
import apiserver.apis.v1_0.images.models.images.ImageRotateModel;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.ServletException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/flows/image/imageRotate-flow.xml"})
public class ImageRotateTest
{
    public final Logger log = LoggerFactory.getLogger(ImageRotateTest.class);

    @Autowired
    private ImageRotateGateway gateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;

    static File file = null;

    @BeforeClass
    public static void beforeClass()
    {
        try
        {
            String fileName = "IMG_5932_sm.png";
            file = new File(ImageInfoTest.class.getClassLoader().getSystemResource(fileName).toURI());

        }catch (Exception ex){
            org.junit.Assert.fail(ex.getMessage());
        }
    }


    @Test
    public void testPngResize() throws ServletException, IOException, Exception
    {
        int width = 296;
        int height = 500;

        try
        {
            ImageRotateModel args = new ImageRotateModel();
            args.setAngle(90);
            args.setFile(file);

            Future<Map> resultFuture = gateway.rotateImage(args);
            ImageRotateModel result = (ImageRotateModel)resultFuture.get( defaultTimeout, TimeUnit.MILLISECONDS );


            BufferedImage bimg =  result.getProcessedFile();
            int imgWidth          = bimg.getWidth();
            int imgHeight         = bimg.getHeight();

            org.springframework.util.Assert.isTrue(imgWidth == width);
            org.springframework.util.Assert.isTrue(imgHeight == height);
        }
        catch (Exception ex){
            ex.printStackTrace();
            org.junit.Assert.fail(ex.getMessage());
        }

    }


}