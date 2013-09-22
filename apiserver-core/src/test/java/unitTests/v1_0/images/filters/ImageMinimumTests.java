package unitTests.v1_0.images.filters;

import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterMinimumGateway;
import apiserver.core.common.ResponseEntityHelper;
import apiserver.core.models.FileModel;
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
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/apis-servlet-test.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/flows/image-filters/filterMinimum-flow.xml"})
public class ImageMinimumTests
{
    public final Logger log = LoggerFactory.getLogger(ImageMinimumTests.class);

    @Autowired
    private ApiImageFilterMinimumGateway imageMinimumFilterGateway;

    static File file = null;

    @BeforeClass
    public static void setup() throws URISyntaxException
    {
        file = new File(  ImageMinimumTests.class.getClassLoader().getResource("sample.png").toURI()  );
    }



    @Test
    public void testMinimumByFile() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        FileModel args = new FileModel();
        args.setFile(file);


        Future<Map> imageFuture = imageMinimumFilterGateway.imageMinimumFilter(args);

        FileModel payload = (FileModel)imageFuture.get(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = (BufferedImage)payload.getBufferedImage();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );


        String contentType = (String)payload.getContentType();
        Assert.assertTrue("NULL ContentType in payload", contentType != null );


        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.FALSE);
        Assert.assertEquals("Invalid image bytes",  286745, result.getBody().length);
    }


    @Test
    public void testMinimumBase64ByFile() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        FileModel args = new FileModel();
        args.setFile(file);

        Future<Map> imageFuture = imageMinimumFilterGateway.imageMinimumFilter(args);

        FileModel payload = (FileModel)imageFuture.get(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = (BufferedImage)payload.getBufferedImage();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );


        String contentType = (String)payload.getContentType();
        Assert.assertTrue("NULL ContentType in payload", contentType != null );


        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.TRUE);
        Assert.assertEquals("Invalid image bytes",  382328, result.getBody().length);
    }
}
