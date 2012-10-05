package integrationTests.v1_0.images;

import integrationTests.v1_0.HttpTest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/26/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:**/config/v1_0/apis-servlet-test.xml"})
@Profile("dev")
@Category(categories.ColdFusionTests.class)
public class ImageRotateIT extends HttpTest
{



    @Test
    public void testPngRotate() throws ServletException, IOException, Exception
    {
        int width = 296;
        int height = 500;
        String fileName = "IMG_5932_sm.png";
        String url = "/v1-0/image/rotate";

        File file = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("angle", 90);

        int status = 0;
        // Here we go!
        HttpResponse response = invokeHttpPost(url, file, args);
        status = response.getStatusLine().getStatusCode();


        Assert.isTrue(status == HttpStatus.SC_OK, response.getStatusLine().toString());

        BufferedImage bimg = ImageIO.read(response.getEntity().getContent());
        int imgWidth          = bimg.getWidth();
        int imgHeight         = bimg.getHeight();

        Assert.isTrue( imgWidth == width);
        Assert.isTrue(imgHeight == height);
    }



    @Test
    public void testJpgRotate() throws ServletException, IOException, Exception
    {
        int width = 3000;
        int height = 4000;
        String fileName = "IMG_5932.JPG";
        String url = "/v1-0/image/rotate";

        File file = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("angle", 90);

        int status = 0;
        // Here we go!
        HttpResponse response = invokeHttpPost(url, file, args);
        status = response.getStatusLine().getStatusCode();


        Assert.isTrue(status == HttpStatus.SC_OK, response.getStatusLine().toString());

        BufferedImage bimg = ImageIO.read(  response.getEntity().getContent()  );
        int imgWidth          = bimg.getWidth();
        int imgHeight         = bimg.getHeight();

        Assert.isTrue( imgWidth == width);
        Assert.isTrue(imgHeight == height);
    }
}
