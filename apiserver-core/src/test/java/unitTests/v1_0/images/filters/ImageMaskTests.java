package unitTests.v1_0.images.filters;

import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterMaskGateway;
import apiserver.apis.v1_0.images.models.filters.MaskModel;
import apiserver.core.common.ResponseEntityHelper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:apiserver-core/src/main/webapp/WEB-INF/config/application-context-test.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/apis-servlet-test.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/flows/image/filters/filterMask-flow.xml"})
public class ImageMaskTests
{
    public final Logger log = LoggerFactory.getLogger(ImageMaskTests.class);

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;

    @Autowired
    private ApiImageFilterMaskGateway imageMaskFilterGateway;

    static File file = null;
    static File maskFile = null;

    @BeforeClass
    public static void setup() throws URISyntaxException
    {
        file = new File(  ImageMaskTests.class.getClassLoader().getResource("sample.png").toURI()  );
        maskFile = new File(  ImageMaskTests.class.getClassLoader().getResource("sample_mask.jpeg").toURI()  );
    }



    @Test
    public void testMaskByFile() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        MaskModel args = new MaskModel();
        args.setFile(file);
        args.setMask(maskFile);

        Future<Map> imageFuture = imageMaskFilterGateway.imageMaskFilter(args);

        MaskModel payload = (MaskModel)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = payload.getBufferedImage();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );

        String contentType = payload.getContentType();
        Assert.assertEquals("image/png",contentType);

        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.FALSE);
        Assert.assertEquals("Invalid image bytes",  391263, result.getBody().length);
    }


    @Test
    public void testMaskBase64ByFile() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        MaskModel args = new MaskModel();
        args.setFile(file);
        args.setMask(maskFile);

        Future<Map> imageFuture = imageMaskFilterGateway.imageMaskFilter(args);

        MaskModel payload = (MaskModel)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = payload.getBufferedImage();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );

        String contentType = payload.getContentType();
        Assert.assertEquals("image/png",contentType);

        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.TRUE);
        Assert.assertEquals("Invalid image bytes",  521684, result.getBody().length);
    }
}
