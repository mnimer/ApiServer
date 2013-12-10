package apiserver.apis.v1_0.images.controllers.manipulations;

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

import apiserver.apis.v1_0.documents.model.Document;
import apiserver.apis.v1_0.images.gateways.images.ImageDrawBorderGateway;
import apiserver.apis.v1_0.images.gateways.images.ImageDrawTextGateway;
import apiserver.apis.v1_0.images.gateways.jobs.images.FileBorderJob;
import apiserver.apis.v1_0.images.gateways.jobs.images.FileTextJob;
import apiserver.core.common.ResponseEntityHelper;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * User: mnimer
 * Date: 9/18/12
 */
//Controller
@RequestMapping("/image")
public class BorderController
{
    @Autowired
    private ImageDrawBorderGateway imageDrawBorderGateway;

    @Autowired
    private ImageDrawTextGateway imageDrawTextGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;


    /**
     * Draw a border around an image
     *
     * @param documentId
     * @param color
     * @param thickness
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws IOException
     */
    @RequestMapping(value = "/{documentId}/border", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> drawBorderByImage(
            @ApiParam(name = "documentId", required = true) @PathVariable(value = "documentId") String documentId
            , @ApiParam(name="color", required = true) @RequestParam(required = true) String color
            , @ApiParam(name="thickness", required = true) @RequestParam(required = true) Integer thickness
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        FileBorderJob args = new FileBorderJob();
        args.setDocumentId(documentId);
        args.setColor(color);
        args.setThickness(thickness);

        Future<Map> imageFuture = imageDrawBorderGateway.imageDrawBorderFilter(args);
        FileBorderJob payload = (FileBorderJob)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getBufferedImage();
        String contentType = payload.getDocument().getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, false);
        return result;
    }



    /**
     * Draw a border around an image
     *
     * @param file
     * @param color
     * @param thickness
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws IOException
     */
    @RequestMapping(value = "/border", method = {RequestMethod.POST})
    public ResponseEntity<byte[]> drawBorderByImage(
            @ApiParam(name = "file", required = true) @RequestParam(value = "file") MultipartFile file
            , @ApiParam(name="color", required = true) @RequestParam(required = true) String color
            , @ApiParam(name="thickness", required = true) @RequestParam(required = true) Integer thickness
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        FileBorderJob job = new FileBorderJob();
        job.setDocumentId(null);
        job.setDocument(new Document(file));
        job.getDocument().setContentType(file.getContentType());
        job.getDocument().setFileName(file.getOriginalFilename());
        job.setColor(color);
        job.setThickness(thickness);

        Future<Map> imageFuture = imageDrawBorderGateway.imageDrawBorderFilter(job);
        FileBorderJob payload = (FileBorderJob)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getBufferedImage();
        String contentType = payload.getDocument().getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, false);
        return result;
    }


}