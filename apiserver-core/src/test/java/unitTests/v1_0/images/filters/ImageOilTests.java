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
import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterOilGateway;
import apiserver.apis.v1_0.images.gateways.jobs.filters.OilJob;
import apiserver.core.common.ResponseEntityHelper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/flows/image/filters/filterOil-flow.xml"})
public class ImageOilTests
{
    public final Logger log = LoggerFactory.getLogger(ImageOilTests.class);

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;


    @Autowired
    private ApiImageFilterOilGateway imageOilFilterGateway;


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
    public void testOilByFile() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        OilJob args = new OilJob();
        args.setDocumentId(documentId);
        args.setLevels(3);
        args.setRange(256);

        Future<Map> imageFuture = imageOilFilterGateway.imageOilFilter(args);

        OilJob payload = (OilJob)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = (BufferedImage)payload.getBufferedImage();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );

        String contentType = (String)payload.getDocument().getContentType().name();
        Assert.assertTrue("NULL ContentType in payload", contentType != null );

        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.FALSE);
        Assert.assertEquals("Invalid image bytes",  391263, result.getBody().length);
    }


    @Test
    public void testOilBase64ByFile() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        OilJob args = new OilJob();
        args.setDocumentId(documentId);
        args.setLevels(3);
        args.setRange(256);

        Future<Map> imageFuture = imageOilFilterGateway.imageOilFilter(args);

        OilJob payload = (OilJob)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = (BufferedImage)payload.getBufferedImage();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );

        String contentType = (String)payload.getDocument().getContentType().name();
        Assert.assertTrue("NULL ContentType in payload", contentType != null );

        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.TRUE);
        Assert.assertEquals("Invalid image bytes",  521684, result.getBody().length);
    }
}
