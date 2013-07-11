package unitTests.v1_0.image_filters;

import apiserver.apis.v1_0.common.ResponseEntityHelper;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterBoxBlurGateway;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * User: mikenimer
 * Date: 7/7/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:apiserver-core/src/main/webapp/WEB-INF/config/application-context-test.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/apis-servlet-test.xml"})
public class ImageBoxBlurTests
{
    public final Logger log = LoggerFactory.getLogger(ImageBoxBlurTests.class);

    @Autowired
    private ApiImageFilterBoxBlurGateway imageBoxBlurFilterGateway;

    String defaultCacheId = "a3c8af38-82e3-4241-8162-28e17ebcbf52";
    static File file = null;

    @BeforeClass
    public static void setup() throws URISyntaxException
    {
        file = new File(  ImageBoxBlurTests.class.getClassLoader().getResource("sample.png").toURI()  );
    }

    @Test
    public void testBoxBlurById() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        Future<Map> imageFuture = imageBoxBlurFilterGateway.imageBoxBlurFilter(defaultCacheId, 2, 2, 1, true, false);
        Map payload = imageFuture.get(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = (BufferedImage)payload.get(ImageConfigMBeanImpl.RESULT);
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );


        String contentType = (String)payload.get(ImageConfigMBeanImpl.CONTENT_TYPE);
        Assert.assertTrue("NULL ContentType in payload", contentType != null );


        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.FALSE);
        Assert.assertEquals("Invalid image bytes",  391263, result.getBody().length);
    }



    @Test
    public void testBoxBlurBase64ById() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        Future<Map> imageFuture = imageBoxBlurFilterGateway.imageBoxBlurFilter(defaultCacheId, 2, 2, 1, true, true);
        Map payload = imageFuture.get(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = (BufferedImage)payload.get(ImageConfigMBeanImpl.RESULT);
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );


        String contentType = (String)payload.get(ImageConfigMBeanImpl.CONTENT_TYPE);
        Assert.assertTrue("NULL ContentType in payload", contentType != null );


        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.TRUE);
        Assert.assertEquals("Invalid image bytes",  521684, result.getBody().length);
    }




    @Test
    public void testBoxBlurByFile() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        Future<Map> imageFuture = imageBoxBlurFilterGateway.imageBoxBlurFilter( file, 2, 2, 1, true, false );
        Map payload = imageFuture.get(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = (BufferedImage)payload.get(ImageConfigMBeanImpl.RESULT);
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );


        String contentType = (String)payload.get(ImageConfigMBeanImpl.CONTENT_TYPE);
        Assert.assertTrue("NULL ContentType in payload", contentType != null );


        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.FALSE);
        Assert.assertEquals("Invalid image bytes",  391263, result.getBody().length);
    }


    @Test
    public void testBoxBlurBase64ByFile() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        Future<Map> imageFuture = imageBoxBlurFilterGateway.imageBoxBlurFilter( file, 2, 2, 1, true, true );
        Map payload = imageFuture.get(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = (BufferedImage)payload.get(ImageConfigMBeanImpl.RESULT);
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );


        String contentType = (String)payload.get(ImageConfigMBeanImpl.CONTENT_TYPE);
        Assert.assertTrue("NULL ContentType in payload", contentType != null );


        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.TRUE);
        Assert.assertEquals("Invalid image bytes",  521684, result.getBody().length);
    }
}
