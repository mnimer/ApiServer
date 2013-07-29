package unitTests.v1_0.image_filters;

import apiserver.apis.v1_0.common.ResponseEntityHelper;
import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterMinimumGateway;
import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterMotionBlurGateway;
import apiserver.apis.v1_0.images.models.ImageModel;
import apiserver.apis.v1_0.images.models.filters.MotionBlurModel;
import com.wordnik.swagger.annotations.ApiParam;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
 * Date: 7/10/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:apiserver-core/src/main/webapp/WEB-INF/config/application-context-test.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/apis-servlet-test.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/flows/image-filters/filterMotionBlur-flow.xml"})
public class ImageMotionBlurTests
{
    public final Logger log = LoggerFactory.getLogger(ImageMinimumTests.class);

    @Autowired
    private ApiImageFilterMotionBlurGateway imageMotionBlurFilterGateway;

    String defaultCacheId = "a3c8af38-82e3-4241-8162-28e17ebcbf52";
    static File file = null;

    @BeforeClass
    public static void setup() throws URISyntaxException
    {
        file = new File(  ImageMotionBlurTests.class.getClassLoader().getResource("sample.png").toURI()  );
    }

    @Test
    public void testMotionBlurById() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        MotionBlurModel args = new MotionBlurModel();
        args.setCacheId(defaultCacheId);
        args.setAngle(0);
        args.setDistance(0);
        args.setRotation(0);
        args.setWrapEdges(false);
        args.setZoom(0);


        Future<Map> imageFuture = imageMotionBlurFilterGateway.imageMotionBlurFilter(args);
        ImageModel payload = (ImageModel)imageFuture.get(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null);

        BufferedImage bufferedImage = (BufferedImage)payload.getProcessedFile();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );

        String contentType = (String)payload.getContentType();
        Assert.assertTrue("NULL ContentType in payload", contentType != null );


        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.FALSE);
        Assert.assertEquals("Invalid image bytes",  411345, result.getBody().length);
    }



    @Test
    public void testMotionBlur64ById() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        MotionBlurModel args = new MotionBlurModel();
        args.setCacheId(defaultCacheId);
        args.setAngle(0);
        args.setDistance(0);
        args.setRotation(0);
        args.setWrapEdges(false);
        args.setZoom(0);


        Future<Map> imageFuture = imageMotionBlurFilterGateway.imageMotionBlurFilter(args);

        ImageModel payload = (ImageModel)imageFuture.get(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null);

        BufferedImage bufferedImage = (BufferedImage)payload.getProcessedFile();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );

        String contentType = (String)payload.getContentType();
        Assert.assertTrue("NULL ContentType in payload", contentType != null );


        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.TRUE);
        Assert.assertEquals("Invalid image bytes",  548460, result.getBody().length);
    }




    @Test
    public void testMotionBlurByFile() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        MotionBlurModel args = new MotionBlurModel();
        args.setCacheId(defaultCacheId);
        args.setAngle(0);
        args.setDistance(0);
        args.setRotation(0);
        args.setWrapEdges(false);
        args.setZoom(0);


        Future<Map> imageFuture = imageMotionBlurFilterGateway.imageMotionBlurFilter(args);

        ImageModel payload = (ImageModel)imageFuture.get(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null);

        BufferedImage bufferedImage = (BufferedImage)payload.getProcessedFile();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );

        String contentType = (String)payload.getContentType();
        Assert.assertTrue("NULL ContentType in payload", contentType != null );


        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.FALSE);
        Assert.assertEquals("Invalid image bytes",  411345, result.getBody().length);
    }


    @Test
    public void testMotionBlurBase64ByFile() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        MotionBlurModel args = new MotionBlurModel();
        args.setCacheId(defaultCacheId);
        args.setAngle(0);
        args.setDistance(0);
        args.setRotation(0);
        args.setWrapEdges(false);
        args.setZoom(0);


        Future<Map> imageFuture = imageMotionBlurFilterGateway.imageMotionBlurFilter(args);

        ImageModel payload = (ImageModel)imageFuture.get(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null);

        BufferedImage bufferedImage = (BufferedImage)payload.getProcessedFile();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );

        String contentType = (String)payload.getContentType();
        Assert.assertTrue("NULL ContentType in payload", contentType != null );


        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.TRUE);
        Assert.assertEquals("Invalid image bytes",  548460, result.getBody().length);
    }
}
