package unitTests.v1_0.images.filters;

import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterGlowGateway;
import apiserver.apis.v1_0.images.models.filters.GlowModel;
import apiserver.core.common.ResponseEntityHelper;
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

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
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
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/apis-servlet-test.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/flows/image/filters/filterGlow-flow.xml"})
public class ImageGlowTests
{
    public final Logger log = LoggerFactory.getLogger(ImageGlowTests.class);

    @Resource(name="supportedMimeTypes")
    public HashMap<String, String> supportedMimeTypes;

    @Autowired
    private ApiImageFilterGlowGateway imageGlowFilterGateway;

    static File file = null;

    @BeforeClass
    public static void setup() throws URISyntaxException
    {
        file = new File(  ImageGlowTests.class.getClassLoader().getResource("sample.png").toURI()  );
    }




    @Test
    public void testGlowByFile() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        GlowModel args = new GlowModel();
        args.supportedMimeTypes = supportedMimeTypes;
        args.setFile(file);
        args.setAmount(2);

        Future<Map> imageFuture = imageGlowFilterGateway.imageGlowFilter(args);

        GlowModel payload = (GlowModel)imageFuture.get(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = payload.getBufferedImage();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );

        String contentType = payload.getContentType();
        Assert.assertEquals("image/png",contentType);


        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.FALSE);
        Assert.assertEquals("Invalid image bytes",  40561, result.getBody().length);
    }


    @Test
    public void testGlowBase64ByFile() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        GlowModel args = new GlowModel();
        args.supportedMimeTypes = supportedMimeTypes;
        args.setFile(file);
        args.setAmount(2);

        Future<Map> imageFuture = imageGlowFilterGateway.imageGlowFilter(args);

        GlowModel payload = (GlowModel)imageFuture.get(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = payload.getBufferedImage();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );

        String contentType = payload.getContentType();
        Assert.assertEquals("image/png",contentType);

        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.TRUE);
        Assert.assertEquals("Invalid image bytes",  54084, result.getBody().length);
    }
}
