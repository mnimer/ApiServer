package filters;

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

import apiserver.core.common.ResponseEntityHelper;
import apiserver.services.cache.DocumentJob;
import apiserver.services.cache.gateway.CacheGateway;
import apiserver.services.cache.gateway.jobs.DeleteDocumentJob;
import apiserver.services.cache.gateway.jobs.UploadDocumentJob;
import apiserver.services.cache.model.Document;
import apiserver.services.images.gateways.filters.ApiImageFilterGlowGateway;
import apiserver.services.images.gateways.jobs.filters.GlowJob;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.ResponseEntity;
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
@ImportResource("flows/filters/filterGlow-flow.xml")
public class ImageGlowTests
{

    @Autowired
    private ApiImageFilterGlowGateway imageGlowFilterGateway;


    @Qualifier("documentAddGateway")
    @Autowired(required = false)
    private CacheGateway documentGateway;


    @Qualifier("documentDeleteGateway")
    @Autowired(required = false)
    private CacheGateway documentDeleteGateway;



    String documentId = null;

    @Before
    public void setup() throws URISyntaxException, IOException, InterruptedException, ExecutionException
    {
        File file = new File(  ImageGlowTests.class.getClassLoader().getResource("IMG_5932.JPG").toURI()  );

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
        documentDeleteGateway.deleteDocument(job).get();
    }





    @Test
    public void testGlowByFile() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        GlowJob args = new GlowJob();
        args.setDocumentId(documentId);
        args.setAmount(2);

        Future<Map> imageFuture = imageGlowFilterGateway.imageGlowFilter(args);

        GlowJob payload = (GlowJob)imageFuture.get(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = payload.getBufferedImage();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );

        String contentType = payload.getDocument().getContentType().contentType;
        Assert.assertEquals("image/jpeg",contentType);


        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.FALSE);
        Assert.assertEquals("Invalid image bytes",  2713917, result.getBody().length);
    }


    @Test
    public void testGlowBase64ByFile() throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        GlowJob args = new GlowJob();
        args.setDocumentId(documentId);
        args.setAmount(2);

        Future<Map> imageFuture = imageGlowFilterGateway.imageGlowFilter(args);

        GlowJob payload = (GlowJob)imageFuture.get(10000, TimeUnit.MILLISECONDS);
        Assert.assertTrue("NULL Payload", payload != null );

        BufferedImage bufferedImage = payload.getBufferedImage();
        Assert.assertTrue("NULL BufferedImage in payload", bufferedImage != null );

        String contentType = payload.getDocument().getContentType().contentType;
        Assert.assertEquals("image/jpeg",contentType);

        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, Boolean.TRUE);
        Assert.assertEquals("Invalid image bytes",  3618556, result.getBody().length);
    }
}