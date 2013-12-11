package apiserver.apis.v1_0.images.controllers.filters;

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

import apiserver.apis.v1_0.MimeType;
import apiserver.apis.v1_0.documents.model.Document;
import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterBlurGateway;
import apiserver.apis.v1_0.images.gateways.jobs.ImageDocumentJob;
import apiserver.core.common.ResponseEntityHelper;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * User: mikenimer
 * Date: 8/29/13
 */
@Controller
@RequestMapping("/image-filters")
@Api(value="/image/filters", description = "[IMAGE] Image Filter APIs")
public class BlurController
{
    public final Logger log = LoggerFactory.getLogger(BlurController.class);

    @Autowired
    private ApiImageFilterBlurGateway imageFilterBlurGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;


    /**
     * This filter blurs an uploaded image very slightly using a 3x3 blur kernel.
     *
     * @param documentId
     * @return
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    @ApiOperation(value = "This filter blurs an image very slightly using a 3x3 blur kernel.")
    @RequestMapping(value = "/{documentId}/blur", method = RequestMethod.GET)
    public ResponseEntity<byte[]> imageBlurById(
            @ApiParam(name = "documentId", required = true,  defaultValue = "8D981024-A297-4169-8603-E503CC38EEDA")
            @PathVariable(value = "documentId") String documentId
    ) throws TimeoutException, ExecutionException, InterruptedException, IOException, URISyntaxException
    {
        ImageDocumentJob job = new ImageDocumentJob();
        job.setDocumentId(documentId);

        Future<Map> imageFuture = imageFilterBlurGateway.imageBlurFilter(job);
        ImageDocumentJob payload = (ImageDocumentJob) imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        //BufferedImage bufferedImage = payload.getBufferedImage();
        String contentType = payload.getDocument().getContentType().name();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processFile(payload.getDocument().getFileBytes(), contentType, false);
        return result;
    }


    @ApiOperation(value = "This filter blurs an image very slightly using a 3x3 blur kernel.")
    @RequestMapping(value = "/blur", method = RequestMethod.POST)
    public ResponseEntity<byte[]> imageBlurByFile(
            @ApiParam(name = "file", required = true)
            @RequestParam(value = "file", required = true) MultipartFile file
    ) throws TimeoutException, ExecutionException, InterruptedException, IOException, URISyntaxException
    {
        ImageDocumentJob job = new ImageDocumentJob();
        job.setDocumentId(null);
        job.setDocument(new Document(file));
        job.getDocument().setContentType(MimeType.getMimeType(file.getContentType()) );
        job.getDocument().setFileName( file.getOriginalFilename() );

        Future<Map> imageFuture = imageFilterBlurGateway.imageBlurFilter(job);
        ImageDocumentJob payload = (ImageDocumentJob) imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        //BufferedImage bufferedImage = payload.getBufferedImage();
        String contentType = payload.getDocument().getContentType().name();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processFile(payload.getDocument().getFileBytes(), contentType, false);
        return result;
    }

}
