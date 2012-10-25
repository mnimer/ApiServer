package integrationTests.v1_0.images;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MapFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * User: mnimer
 * Date: 9/20/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:**/config/v1_0/apis-servlet-integration-tests.xml"})
@Profile("dev")
@Category(categories.ColdFusionTests.class)
public class CaptchaGeneratorIT
{
    @Autowired
    ApplicationContext context;

    @Autowired
    public MapFactoryBean unitTestProperties;


    private String getUrlBase() throws Exception
    {
        String host = unitTestProperties.getObject().get("tomcatHost").toString();
        String port = unitTestProperties.getObject().get("tomcatPort").toString();
        String contextRoot = unitTestProperties.getObject().get("tomcatContextRoot").toString();

        return "http://" +host +":" +port +contextRoot;
    }


    @Test
    public void testHighCaptcha() throws ServletException, IOException, Exception
    {
        int width = 232;
        int height = 167;
        String difficulty = "high";


        URL url = new URL( getUrlBase() +"/rest/v1/image/captcha/generate?text=Super&width=" +width +"&height=" +height +"&difficulty=" +difficulty +"&fontSize=40");
        //BufferedReader in = new BufferedReader( new InputStreamReader(url.openStream()) );

        BufferedImage bimg = ImageIO.read(url);
        int imgWidth          = bimg.getWidth();
        int imgHeight         = bimg.getHeight();

        Assert.isTrue( imgWidth == width);
        Assert.isTrue( imgHeight == height);

        //in.close();
    }


    @Test
    public void testMediumCaptcha() throws ServletException, IOException, Exception
    {
        int width = 232;
        int height = 167;
        String difficulty = "MEDIUM";


        URL url = new URL( getUrlBase() +"/rest/v1/image/captcha/generate?text=Super&width=" +width +"&height=" +height +"&difficulty=" +difficulty +"&fontSize=40");
        //BufferedReader in = new BufferedReader( new InputStreamReader(url.openStream()) );

        BufferedImage bimg = ImageIO.read(url);
        int imgWidth          = bimg.getWidth();
        int imgHeight         = bimg.getHeight();

        Assert.isTrue( imgWidth == width);
        Assert.isTrue( imgHeight == height);

        //in.close();
    }


    @Test
    public void testLowCaptcha() throws ServletException, IOException, Exception
    {
        int width = 232;
        int height = 167;
        String difficulty = "low";


        URL url = new URL( getUrlBase() +"/rest/v1/image/captcha/generate?text=Super&width=" +width +"&height=" +height +"&difficulty=" +difficulty +"&fontSize=40");
        //BufferedReader in = new BufferedReader( new InputStreamReader(url.openStream()) );

        BufferedImage bimg = ImageIO.read(url);
        int imgWidth          = bimg.getWidth();
        int imgHeight         = bimg.getHeight();

        Assert.isTrue( imgWidth == width);
        Assert.isTrue( imgHeight == height);

        //in.close();
    }

    @Test
    public void testBadDifficultyCaptcha() throws ServletException, IOException, Exception
    {
        int width = 232;
        int height = 167;
        String difficulty = "low low low";


        URL url = new URL( getUrlBase() +"/rest/v1/image/captcha/generate?text=Super&width=" +width +"&height=" +height +"&difficulty=" +difficulty +"&fontSize=40");
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
