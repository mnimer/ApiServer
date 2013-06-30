package integrationTests.v1_0.images;

import integrationTests.v1_0.HttpTest;
import org.apache.http.HttpResponse;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/20/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/apis-servlet-integration-tests.xml"})
@Profile("dev")
@Category(categories.ColdFusionTests.class)
public class CaptchaGeneratorIT  extends HttpTest
{
    @Autowired
    ApplicationContext context;


    @Test
    public void testHighCaptcha() throws Exception
    {
        int width = 232;
        int height = 167;
        String difficulty = "high";

        String url = "/rest/v1/image/captcha/generate";
        Map args = new HashMap();
        args.put("text", "Super");
        args.put("width", width);
        args.put("height", height);
        args.put("difficulty", difficulty);
        args.put("fontSize", 40);
        HttpResponse response = invokeHttpGet(url, (File)null, args);

        BufferedImage bimg = ImageIO.read(  response.getEntity().getContent()  );
        int imgWidth          = bimg.getWidth();
        int imgHeight         = bimg.getHeight();

        Assert.isTrue( imgWidth == width);
        Assert.isTrue( imgHeight == height);

        //in.close();
    }


    @Test
    public void testMediumCaptcha() throws Exception
    {
        int width = 232;
        int height = 167;
        String difficulty = "MEDIUM";


        URL url = new URL( "/rest/v1/image/captcha/generate?text=Super&width=" +width +"&height=" +height +"&difficulty=" +difficulty +"&fontSize=40");
        //BufferedReader in = new BufferedReader( new InputStreamReader(url.openStream()) );

        BufferedImage bimg = ImageIO.read(url);
        int imgWidth          = bimg.getWidth();
        int imgHeight         = bimg.getHeight();

        Assert.isTrue( imgWidth == width);
        Assert.isTrue( imgHeight == height);

        //in.close();
    }


    @Test
    public void testLowCaptcha() throws Exception
    {
        int width = 232;
        int height = 167;
        String difficulty = "low";


        URL url = new URL( "/rest/v1/image/captcha/generate?text=Super&width=" +width +"&height=" +height +"&difficulty=" +difficulty +"&fontSize=40");
        //BufferedReader in = new BufferedReader( new InputStreamReader(url.openStream()) );

        BufferedImage bimg = ImageIO.read(url);
        int imgWidth          = bimg.getWidth();
        int imgHeight         = bimg.getHeight();

        Assert.isTrue( imgWidth == width);
        Assert.isTrue( imgHeight == height);

        //in.close();
    }

    @Test
    public void testBadDifficultyCaptcha() throws Exception
    {
        int width = 232;
        int height = 167;
        String difficulty = "low low low";


        URL url = new URL( "/rest/v1/image/captcha/generate?text=Super&width=" +width +"&height=" +height +"&difficulty=" +difficulty +"&fontSize=40");
        //BufferedReader in = new BufferedReader( new InputStreamReader(url.openStream()) );

        try
        {
            BufferedImage bimg = ImageIO.read(url);
            int imgWidth          = bimg.getWidth();
            int imgHeight         = bimg.getHeight();

            throw new RuntimeException("Exception epected with bad difficulty argument");
        }
        catch (Exception ex){}

        //in.close();
    }
}
