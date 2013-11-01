package unitTests.v1_0.images.filters;

/*******************************************************************************
 Copyright (c) 2013 Mike Nimer.

 This file is part of ApiServer Project.

 The ApiServer Project is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The ApiServer Project is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with the ApiServer Project.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

import apiserver.apis.v1_0.documents.DocumentJob;
import apiserver.apis.v1_0.documents.gateway.DocumentGateway;
import apiserver.apis.v1_0.documents.gateway.jobs.DeleteDocumentJob;
import apiserver.apis.v1_0.documents.gateway.jobs.UploadDocumentJob;
import apiserver.apis.v1_0.documents.model.Document;
import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterLensBlurGateway;
import apiserver.apis.v1_0.images.gateways.jobs.filters.LensBlurJob;
import apiserver.core.common.ResponseEntityHelper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/flows/image/filters/filterLensBlur-flow.xml"})
public class ImageLensBlurTests
{
    public final Logger log = LoggerFactory.getLogger(ImageLensBlurTests.class);

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;

    @Resource(name="supportedMimeTypes")
    public HashMap<String, String> supportedMimeTypes;

    @Autowired
    private ApiImageFilterLensBlurGateway imageLensBlurFilterGateway;


    @Qualifier("documentAddGateway")
    @Autowired
    private DocumentGateway documentGateway;

    String documentId = null;

    @Before
    public void setup() throws URISyntaxException, IOException, InterruptedException, ExecutionException
    {
        File file = new File(  ImageMotionBlurTests.class.getClassLoader().getResource("sample.png").toURI()  );

        UploadDocumentJob job = new UploadDocumentJob(file);
        job.setDocument(new Document(file));
        Future<DocumentJob> doc = documentGateway.addDocument(job);
        documentId = ((DocumentJob)doc.get()).getDocument().getId();
    }

    @After
    public void tearDown() throws InterruptedException, ExecutionException
    {
        DeleteDocumentJob job = new DeleteDocumentJob();
        job.setDocumentId(documentId);
        documentGateway.deleteDocument(job).get();
    }



    @Test
    public void testLensBlurByFile() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        LensBlurJob args = new LensBlurJob();
        args.setDocumentId(documentId);
        args.setRadius(10);
        args.setSides(5);
        args.setBloom(2);

        Future<Map> imageFuture = imageLensBlurFilterGateway.imageLensBlurFilter(args);

        LensBlurJob payload = (LensBlurJob)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = payload.getBufferedImage();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );

        String contentType = payload.getDocument().getContentType();
        Assert.assertEquals("image/png",contentType);

        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.FALSE);
        Assert.assertEquals("Invalid image bytes",  392675, result.getBody().length);
    }


    @Test
    public void testLensBlurBase64ByFile() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        LensBlurJob args = new LensBlurJob();
        args.setDocumentId(documentId);
        args.setRadius(10);
        args.setSides(5);
        args.setBloom(2);

        Future<Map> imageFuture = imageLensBlurFilterGateway.imageLensBlurFilter(args);

        LensBlurJob payload = (LensBlurJob)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = payload.getBufferedImage();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );

        String contentType = payload.getDocument().getContentType();
        Assert.assertEquals("image/png",contentType);

        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.TRUE);
        Assert.assertEquals("Invalid image bytes",  523568, result.getBody().length);
    }
}
