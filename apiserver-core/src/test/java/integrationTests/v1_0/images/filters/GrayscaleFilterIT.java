package integrationTests.v1_0.images.filters;

import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import com.jhlabs.image.GrayscaleFilter;
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

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import java.awt.image.BufferedImage;
import java.io.*;
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
public class GrayscaleFilterIT extends HttpTest
{

    public static String key = null;


    @Before
    public void setup() throws Exception
    {
        if (GrayscaleFilterIT.key == null)
        {
            String url = "/rest/v1/image/cache/add";
            String fileName = "IMG_5932_sm.png";
            File file = new File(FiltersBoxBlurIT.class.getClassLoader().getSystemResource(fileName).toURI());

            HttpResponse response = invokeHttpPost(url, file, null);
            int status = response.getStatusLine().getStatusCode();

            Assert.isTrue(status == HttpStatus.SC_OK, "Upload failed with status code: " + status);

            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(in);

            key = root.get(ImageConfigMBeanImpl.KEY).getTextValue();
            Assert.isTrue(key.length() == 36);

            GrayscaleFilterIT.key = key;
        }
    }


    @Test
    public void directTest() throws Exception
    {
        int width = 500;
        int height = 296;
        String fileName = "IMG_5932_sm.png";

        try
        {
            File file = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());
            BufferedImage inFile = ImageIO.read(file);

            // get empty out file
            //BufferedImage outFile = new BufferedImage(inFile.getWidth(), inFile.getHeight(), inFile.getType());

            // Include filter service
            GrayscaleFilter filter = new GrayscaleFilter();

            //filter.setPremultiplyAlpha(((Boolean) props.get("premultiplyAlpha")).booleanValue());
            BufferedImage bimg = filter.filter(inFile, null);

            int imgWidth = bimg.getWidth();
            int imgHeight = bimg.getHeight();

            Assert.isTrue(imgWidth == width);
            Assert.isTrue(imgHeight == height);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw ex;
        }
    }


    @Test
    public void testBoxBlurFilterCache() throws ServletException, IOException, Exception
    {
        int width = 500;
        int height = 296;
        String fileName = "IMG_5932_sm.png";
        String url = "/rest/v1/image/filters/" + GrayscaleFilterIT.key + "/grayscale";


        Map<String, Object> args = new HashMap<String, Object>();
        args.put("hRadius", 2);
        args.put("vRadius", 2);
        args.put("iterations", 2);
        args.put("premultiplyAlpha", true);
        args.put("returnAsBase64", false);

        int status = 0;
        // Here we go!
        HttpResponse response = invokeHttpGet(url, null, args);
        status = response.getStatusLine().getStatusCode();


        Assert.isTrue(status == HttpStatus.SC_OK, response.getStatusLine().toString());


        InputStream is = response.getEntity().getContent();
        BufferedImage bimg = ImageIO.read(is);
        int imgWidth          = bimg.getWidth();
        int imgHeight         = bimg.getHeight();

        Assert.isTrue( imgWidth == width);
        Assert.isTrue(imgHeight == height);

        /**
         *
         *
         * WritableRaster raster = image.getRaster();
         DataBufferByte buffer = (DataBufferByte)raster.getDataBuffer();
         return buffer.getData();
         *

         **/
    }


    @Test
    public void testBoxBlurFilter() throws ServletException, IOException, Exception
    {
        int width = 500;
        int height = 296;
        String fileName = "IMG_5932_sm.png";
        String url = "/rest/v1/image/filters/grayscale";

        File file = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("hRadius", 2);
        args.put("vRadius", 2);
        args.put("iterations", 2);
        args.put("premultiplyAlpha", true);
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
