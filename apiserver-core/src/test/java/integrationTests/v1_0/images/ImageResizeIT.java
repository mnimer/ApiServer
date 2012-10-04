package integrationTests.v1_0.images;

import apiserver.apis.v1_0.images.ImageConfigMBean;
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
@ContextConfiguration(locations = {"file:**/config/v1_0/apis-servlet.xml"})
@Profile("dev")
@Category(categories.ColdFusionTests.class)
public class ImageResizeIT extends HttpTest
{


    @Test
    public void testPngResize() throws ServletException, IOException, Exception
    {
        int width = 250;
        int height = 148;
        String fileName = "IMG_5932_sm.png";
        String url = "/v1-0/image/resize";

        File file = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());

        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.WIDTH, width);
        args.put(ImageConfigMBean.HEIGHT, height);
        args.put(ImageConfigMBean.INTERPOLATION, "bicubic");
        args.put(ImageConfigMBean.SCALE_TO_FIT, false);

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


    @Test
    public void testScaleToFitResize() throws ServletException, IOException, Exception
    {
        int width = 250;
        int height = 148;
        String fileName = "IMG_5932_sm.png";
        String url = "/v1-0/image/resize";

        File file = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());

        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.WIDTH, width);
        args.put(ImageConfigMBean.HEIGHT, height);
        args.put(ImageConfigMBean.INTERPOLATION, "bicubic");
        args.put(ImageConfigMBean.SCALE_TO_FIT, true);

        int status = 0;
        // Here we go!
        HttpResponse response = invokeHttpPost(url, file, args);
        status = response.getStatusLine().getStatusCode();

        //todo, change this once the SCALE_TO_FIT is implemented
        Assert.isTrue(status == HttpStatus.SC_INTERNAL_SERVER_ERROR, response.getStatusLine().toString());
        //Assert.isTrue(status == HttpStatus.SC_OK, response.getStatusLine().toString());

        //BufferedImage bimg = ImageIO.read(  response.getEntity().getContent()  );
        //int imgWidth          = bimg.getWidth();
        //int imgHeight         = bimg.getHeight();

        //Assert.isTrue( imgWidth == width);
        //Assert.isTrue( imgHeight == height);

    }




    @Test
    public void testJpgResize() throws ServletException, IOException, Exception
    {
        int width = 2000;
        int height = 1000;
        String fileName = "IMG_5932.JPG";
        String url = "/v1-0/image/resize";

        File file = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());

        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.WIDTH, width);
        args.put(ImageConfigMBean.HEIGHT, height);
        args.put(ImageConfigMBean.INTERPOLATION, "bicubic");
        args.put(ImageConfigMBean.SCALE_TO_FIT, false);

        int status = 0;
        // Here we go!
        HttpResponse response = invokeHttpPost(url, file, args);
        status = response.getStatusLine().getStatusCode();


        Assert.isTrue(status == HttpStatus.SC_OK, response.getStatusLine().toString());

        BufferedImage bimg = ImageIO.read(  response.getEntity().getContent()  );
        int imgWidth          = bimg.getWidth();
        int imgHeight         = bimg.getHeight();

        Assert.isTrue( imgWidth == width);
        Assert.isTrue( imgHeight == height);
    }


    @Test
    public void testJpgResizeArgs() throws ServletException, IOException, Exception
    {
        int width = 2000;
        int height = 1000;
        String fileName = "IMG_5932.JPG";
        String url = "/v1-0/image/resize";

        File file = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());

        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.WIDTH, width);
        args.put(ImageConfigMBean.HEIGHT, height);
        args.put(ImageConfigMBean.INTERPOLATION, "bicubic");

        int status = 0;
        // Here we go!
        HttpResponse response = invokeHttpPost(url, file, args);
        status = response.getStatusLine().getStatusCode();


        Assert.isTrue(status == HttpStatus.SC_OK, response.getStatusLine().toString());

        BufferedImage bimg = ImageIO.read(  response.getEntity().getContent()  );
        int imgWidth          = bimg.getWidth();
        int imgHeight         = bimg.getHeight();

        Assert.isTrue( imgWidth == width);
        Assert.isTrue( imgHeight == height);
    }

    @Test
    public void testJpgResizeArgs2() throws ServletException, IOException, Exception
    {
        int width = 2000;
        int height = 1000;
        String fileName = "IMG_5932.JPG";
        String url = "/v1-0/image/resize";

        File file = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());

        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.WIDTH, width);
        args.put(ImageConfigMBean.HEIGHT, height);

        int status = 0;
        // Here we go!
        HttpResponse response = invokeHttpPost(url, file, args);
        status = response.getStatusLine().getStatusCode();


        Assert.isTrue(status == HttpStatus.SC_OK, response.getStatusLine().toString());

        BufferedImage bimg = ImageIO.read(  response.getEntity().getContent()  );
        int imgWidth          = bimg.getWidth();
        int imgHeight         = bimg.getHeight();

        Assert.isTrue( imgWidth == width);
        Assert.isTrue( imgHeight == height);
    }


    @Test
    public void testJpgResizeArgs3() throws ServletException, IOException, Exception
    {
        int width = 2000;
        int height = 1000;
        String fileName = "IMG_5932.JPG";
        String url = "/v1-0/image/resize";

        File file = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());

        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.WIDTH, width);

        int status = 0;
        // Here we go!
        HttpResponse response = invokeHttpPost(url, file, args);
        status = response.getStatusLine().getStatusCode();


        Assert.isTrue(status == HttpStatus.SC_BAD_REQUEST, response.getStatusLine().toString());

    }


    @Test
    public void testJpgResizeArgs4() throws ServletException, IOException, Exception
    {
        int width = 2000;
        int height = 1000;
        String fileName = "IMG_5932.JPG";
        String url = "/v1-0/image/resize";

        File file = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());

        Map<String, Object> args = new HashMap<String, Object>();

        int status = 0;
        // Here we go!
        HttpResponse response = invokeHttpPost(url, file, args);
        status = response.getStatusLine().getStatusCode();


        Assert.isTrue(status == HttpStatus.SC_BAD_REQUEST, response.getStatusLine().toString());

    }



    @Test
    public void testJpgResizeBadArgs1() throws ServletException, IOException, Exception
    {
        int width = 2000;
        int height = 1000;
        String fileName = "IMG_5932.JPG";
        String url = "/v1-0/image/resize";

        File file = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());

        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.WIDTH, "small");
        args.put(ImageConfigMBean.HEIGHT, height);
        args.put(ImageConfigMBean.INTERPOLATION, "bicubic");

        int status = 0;
        // Here we go!
        HttpResponse response = invokeHttpPost(url, file, args);
        status = response.getStatusLine().getStatusCode();


        Assert.isTrue(status == HttpStatus.SC_BAD_REQUEST, response.getStatusLine().toString());

    }



    @Test
    public void testJpgResizeBadArgs2() throws ServletException, IOException, Exception
    {
        int width = 2000;
        int height = 1000;
        String fileName = "IMG_5932.JPG";
        String url = "/v1-0/image/resize";

        File file = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());

        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.WIDTH, width);
        args.put(ImageConfigMBean.HEIGHT, height);
        args.put(ImageConfigMBean.INTERPOLATION, "garble");

        int status = 0;
        // Here we go!
        HttpResponse response = invokeHttpPost(url, file, args);
        status = response.getStatusLine().getStatusCode();


        Assert.isTrue(status == HttpStatus.SC_INTERNAL_SERVER_ERROR, response.getStatusLine().toString());

    }

}
