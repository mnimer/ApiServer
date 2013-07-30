package unitTests.v1_0.image_filters;

import apiserver.apis.v1_0.common.ResponseEntityHelper;
import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterOilGateway;
import apiserver.apis.v1_0.images.models.filters.OilModel;
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
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/flows/image-filters/filterOil-flow.xml"})
public class ImageOilTests
{
    public final Logger log = LoggerFactory.getLogger(ImageOilTests.class);

    @Autowired
    private ApiImageFilterOilGateway imageOilFilterGateway;

    String defaultCacheId = "a3c8af38-82e3-4241-8162-28e17ebcbf52";
    static File file = null;

    @BeforeClass
    public static void setup() throws URISyntaxException
    {
        file = new File(  ImageOilTests.class.getClassLoader().getResource("sample.png").toURI()  );
    }

    @Test
    public void testOilById() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        OilModel args = new OilModel();
        args.setCacheId(defaultCacheId);
        args.setLevels(3);
        args.setRange(256);

        Future<Map> imageFuture = imageOilFilterGateway.imageOilFilter(args);
        OilModel payload = (OilModel)imageFuture.get(20000, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = (BufferedImage)payload.getProcessedFile();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );

        String contentType = payload.getContentType();
        Assert.assertTrue("NULL ContentType in payload", contentType != null );


        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.FALSE);
        Assert.assertEquals("Invalid image bytes",  391263, result.getBody().length);
    }



    @Test
    public void testOilBase64ById() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        OilModel args = new OilModel();
        args.setCacheId(defaultCacheId);
        args.setLevels(3);
        args.setRange(256);

        Future<Map> imageFuture = imageOilFilterGateway.imageOilFilter(args);

        Object payload = imageFuture.get(20000, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = (BufferedImage)((OilModel)payload).getProcessedFile();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );

        String contentType = ((OilModel)payload).getContentType();
        Assert.assertTrue("NULL ContentType in payload", contentType != null );

        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.TRUE);
        Assert.assertEquals("Invalid image bytes",  521684, result.getBody().length);
    }




    @Test
    public void testOilByFile() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        OilModel args = new OilModel();
        args.setFile(file);
        args.setLevels(3);
        args.setRange(256);

        Future<Map> imageFuture = imageOilFilterGateway.imageOilFilter(args);

        OilModel payload = (OilModel)imageFuture.get(20000, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = (BufferedImage)payload.getProcessedFile();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );

        String contentType = (String)payload.getContentType();
        Assert.assertTrue("NULL ContentType in payload", contentType != null );

        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.FALSE);
        Assert.assertEquals("Invalid image bytes",  391263, result.getBody().length);
    }


    @Test
    public void testOilBase64ByFile() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        OilModel args = new OilModel();
        args.setFile(file);
        args.setLevels(3);
        args.setRange(256);

        Future<Map> imageFuture = imageOilFilterGateway.imageOilFilter(args);

        OilModel payload = (OilModel)imageFuture.get(20000, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = (BufferedImage)payload.getProcessedFile();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );

        String contentType = (String)payload.getContentType();
        Assert.assertTrue("NULL ContentType in payload", contentType != null );

        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.TRUE);
        Assert.assertEquals("Invalid image bytes",  521684, result.getBody().length);
    }
}
