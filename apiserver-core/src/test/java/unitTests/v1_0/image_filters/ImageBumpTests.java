package unitTests.v1_0.image_filters;

import apiserver.apis.v1_0.common.ResponseEntityHelper;
import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterBumpGateway;
import apiserver.apis.v1_0.images.models.filters.BumpModel;
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
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/flows/image-filters/filterBump-flow.xml"})
public class ImageBumpTests
{
    public final Logger log = LoggerFactory.getLogger(ImageBumpTests.class);

    @Autowired
    private ApiImageFilterBumpGateway imageBumpFilterGateway;

    String defaultCacheId = "a3c8af38-82e3-4241-8162-28e17ebcbf52";
    static File file = null;

    @BeforeClass
    public static void setup() throws URISyntaxException
    {
        file = new File(  ImageBumpTests.class.getClassLoader().getResource("sample.png").toURI()  );
    }

    @Test
    public void testBumpById() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        float[] matrix = new float[]{-1.0F,-1.0F,0.0F,-1.0F,1.0F,1.0F,0.0F,1.0F,1.0F};

        BumpModel args = new BumpModel();
        args.setCacheId(defaultCacheId);
        args.setEdgeAction(1);
        args.setUseAlpha(true);
        args.setMatrix(matrix);

        Future<Map> imageFuture = imageBumpFilterGateway.imageBumpFilter(args);
        BumpModel payload = (BumpModel)imageFuture.get(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = payload.getProcessedFile();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );

        String contentType = payload.getContentType();
        Assert.assertEquals("image/png",contentType);


        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.FALSE);
        Assert.assertEquals("Invalid image bytes",  397541, result.getBody().length);
    }



    @Test
    public void testBumpBase64ById() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        float[] matrix = new float[]{-1.0F,-1.0F,0.0F,-1.0F,1.0F,1.0F,0.0F,1.0F,1.0F};

        BumpModel args = new BumpModel();
        args.setCacheId(defaultCacheId);
        args.setEdgeAction(1);
        args.setUseAlpha(true);
        args.setMatrix(matrix);

        Future<Map> imageFuture = imageBumpFilterGateway.imageBumpFilter(args);

        BumpModel payload = (BumpModel)imageFuture.get(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = payload.getProcessedFile();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );

        String contentType = payload.getContentType();
        Assert.assertEquals("image/png",contentType);


        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.TRUE);
        Assert.assertEquals("Invalid image bytes",  530056, result.getBody().length);
    }




    @Test
    public void testBumpByFile() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        float[] matrix = new float[]{-1.0F,-1.0F,0.0F,-1.0F,1.0F,1.0F,0.0F,1.0F,1.0F};

        BumpModel args = new BumpModel();
        args.setFile(file);
        args.setEdgeAction(1);
        args.setUseAlpha(true);
        args.setMatrix(matrix);

        Future<Map> imageFuture = imageBumpFilterGateway.imageBumpFilter(args);

        BumpModel payload = (BumpModel)imageFuture.get(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = payload.getProcessedFile();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );

        String contentType = payload.getContentType();
        Assert.assertEquals("image/png",contentType);


        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.FALSE);
        Assert.assertEquals("Invalid image bytes",  397541, result.getBody().length);
    }


    @Test
    public void testBumpBase64ByFile() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        float[] matrix = new float[]{-1.0F,-1.0F,0.0F,-1.0F,1.0F,1.0F,0.0F,1.0F,1.0F};

        BumpModel args = new BumpModel();
        args.setFile(file);
        args.setEdgeAction(1);
        args.setUseAlpha(true);
        args.setMatrix(matrix);

        Future<Map> imageFuture = imageBumpFilterGateway.imageBumpFilter(args);

        BumpModel payload = (BumpModel)imageFuture.get(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = payload.getProcessedFile();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );

        String contentType = payload.getContentType();
        Assert.assertEquals("image/png",contentType);


        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.TRUE);
        Assert.assertEquals("Invalid image bytes",  530056, result.getBody().length);
    }
}
