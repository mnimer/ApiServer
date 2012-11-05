package integrationTests.v1_0.images.filters;

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
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 10/31/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:**/config/v1_0/apis-servlet-integration-tests.xml"})
@Profile("dev")
@Category(categories.ColdFusionTests.class)
public class BumpFilterIT extends HttpTest
{


    @Test
    public void directTest() throws Exception
    {
        throw new Exception("Not implemented yet");
    }


    @Test
    public void testFilterCache() throws Exception
    {
        throw new Exception("Not implemented yet");
    }



    @Test
    public void testBumpFilter() throws ServletException, IOException, Exception
    {
        int width = 500;
        int height = 296;
        String fileName = "IMG_5932_sm.png";
        String url = "/rest/v1/image/filters/bump";

        File file = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("edgeAction", 1);
        args.put("useAlpha", true);
        args.put("matrix", "-1.0,-1.0,0.0,-1.0,1.0,1.0,0.0,1.0,1.0");
        args.put("returnAsBase64", false);

        int status = 0;
        // Here we go!
        HttpResponse response = invokeHttpPost(url, file, args);
        status = response.getStatusLine().getStatusCode();

        Assert.isTrue(status == HttpStatus.SC_OK, response.getStatusLine().toString());

        InputStream is = response.getEntity().getContent();
        BufferedImage bimg = ImageIO.read(is);
        int imgWidth          = bimg.getWidth();
        int imgHeight         = bimg.getHeight();

        Assert.isTrue( imgWidth == width);
        Assert.isTrue(imgHeight == height);


    }


}
