package unitTests.v1_0.images.filters;

import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterMotionBlurGateway;
import apiserver.apis.v1_0.images.models.filters.MotionBlurModel;
import apiserver.core.common.ResponseEntityHelper;
import apiserver.core.models.FileModel;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;

    @Autowired
    private ApiImageFilterMotionBlurGateway imageMotionBlurFilterGateway;

    static File file = null;

    @BeforeClass
    public static void setup() throws URISyntaxException
    {
        file = new File(  ImageMotionBlurTests.class.getClassLoader().getResource("sample.png").toURI()  );
    }



    @Test
    public void testMotionBlurByFile() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        MotionBlurModel args = new MotionBlurModel();
        args.setFile(file);
        args.setAngle(0);
        args.setDistance(0);
        args.setRotation(0);
        args.setWrapEdges(false);
        args.setZoom(0);


        Future<Map> imageFuture = imageMotionBlurFilterGateway.imageMotionBlurFilter(args);

        FileModel payload = (FileModel)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null);

        BufferedImage bufferedImage = (BufferedImage)payload.getBufferedImage();
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
        args.setFile(file);
        args.setAngle(0);
        args.setDistance(0);
        args.setRotation(0);
        args.setWrapEdges(false);
        args.setZoom(0);


        Future<Map> imageFuture = imageMotionBlurFilterGateway.imageMotionBlurFilter(args);

        FileModel payload = (FileModel)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null);

        BufferedImage bufferedImage = (BufferedImage)payload.getBufferedImage();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );

        String contentType = (String)payload.getContentType();
        Assert.assertTrue("NULL ContentType in payload", contentType != null );


        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.TRUE);
        Assert.assertEquals("Invalid image bytes",  548460, result.getBody().length);
    }
}
