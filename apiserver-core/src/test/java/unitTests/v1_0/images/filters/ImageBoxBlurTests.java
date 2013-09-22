package unitTests.v1_0.images.filters;

import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterBoxBlurGateway;
import apiserver.apis.v1_0.images.models.filters.BoxBlurModel;
import apiserver.core.common.ResponseEntityHelper;
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
 * Date: 7/7/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:apiserver-core/src/main/webapp/WEB-INF/config/application-context-test.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/apis-servlet-test.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/flows/image-filters/filterBoxBlur-flow.xml"})
public class ImageBoxBlurTests
{
    public final Logger log = LoggerFactory.getLogger(ImageBoxBlurTests.class);

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;

    @Autowired
    private ApiImageFilterBoxBlurGateway imageBoxBlurFilterGateway;

    static File file = null;

    @BeforeClass
    public static void setup() throws URISyntaxException
    {
        file = new File(  ImageBoxBlurTests.class.getClassLoader().getResource("sample.png").toURI()  );
    }



    @Test
    public void testBoxBlurByFile() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        BoxBlurModel args = new BoxBlurModel();
        args.setFile(file);
        args.setHRadius(2);
        args.setVRadius(2);
        args.setIterations(1);
        args.setPreMultiplyAlpha(true);


        Future<Map> imageFuture = imageBoxBlurFilterGateway.imageBoxBlurFilter(args);
        BoxBlurModel payload = (BoxBlurModel)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = payload.getBufferedImage();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );

        String contentType = payload.getContentType();
        Assert.assertEquals("image/png",contentType);


        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.FALSE);
        Assert.assertEquals("Invalid image bytes",  380892, result.getBody().length);
    }


    @Test
    public void testBoxBlurBase64ByFile() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        BoxBlurModel args = new BoxBlurModel();
        args.setFile(file);
        args.setHRadius(2);
        args.setVRadius(2);
        args.setIterations(1);
        args.setPreMultiplyAlpha(true);

        Future<Map> imageFuture = imageBoxBlurFilterGateway.imageBoxBlurFilter(args);
        BoxBlurModel payload = (BoxBlurModel)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = payload.getBufferedImage();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );

        String contentType = payload.getContentType();
        Assert.assertEquals("image/png",contentType);


        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.TRUE);
        Assert.assertEquals("Invalid image bytes",  507856, result.getBody().length);
    }
}
