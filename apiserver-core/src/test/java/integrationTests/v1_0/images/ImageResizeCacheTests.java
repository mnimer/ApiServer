package integrationTests.v1_0.images;

import apiserver.apis.v1_0.images.ImageConfigMBean;
import integrationTests.v1_0.HttpTest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/27/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:**/config/v1_0/apis-servlet.xml"})
@Profile("dev")
@Category(categories.ColdFusionTests.class)
public class ImageResizeCacheTests extends HttpTest
{
    public String key;

    @Before
    public void setup() throws Exception
    {
        String url = "/v1-0/image/cache/add";
        String fileName = "IMG_5932_sm.png";
        File file = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());

        HttpResponse response = invokeHttpPost(url, file, null);
        int status = response.getStatusLine().getStatusCode();

        Assert.isTrue(status == HttpStatus.SC_OK, "Upload failed with status code: " + status);

        BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(in);

        key = root.get(ImageConfigMBean.KEY).getTextValue();
        Assert.isTrue( key.length() == 36 );
    }


    @Test
    public void testPngResize() throws ServletException, IOException, Exception
    {
        int width = 250;
        int height = 148;
        String url = "/v1-0/image/" + key +"/resize";


        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.WIDTH, width);
        args.put(ImageConfigMBean.HEIGHT, height);
        args.put(ImageConfigMBean.INTERPOLATION, "bicubic");
        args.put(ImageConfigMBean.SCALE_TO_FIT, false);


        // Here we go!
        HttpResponse response = invokeHttpGet(url, null, args);
        int status = response.getStatusLine().getStatusCode();


        Assert.isTrue(status == HttpStatus.SC_OK, response.getStatusLine().toString());

        BufferedImage bimg = ImageIO.read(response.getEntity().getContent());
        int imgWidth          = bimg.getWidth();
        int imgHeight         = bimg.getHeight();

        Assert.isTrue( imgWidth == width);
        Assert.isTrue(imgHeight == height);
    }

}
