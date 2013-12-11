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
import apiserver.apis.v1_0.images.gateways.filters.ApiImageFilterMotionBlurGateway;
import apiserver.apis.v1_0.images.gateways.jobs.ImageDocumentJob;
import apiserver.apis.v1_0.images.gateways.jobs.filters.MotionBlurJob;
import apiserver.core.common.ResponseEntityHelper;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * User: mikenimer
 * Date: 9/16/13
 */
@Controller
@RequestMapping("/image-filters")
public class MotionBlurController
{
    public final Logger log = LoggerFactory.getLogger(MotionBlurController.class);

    @Autowired
    private ApiImageFilterMotionBlurGateway imageFilterMotionBlurGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;


    /**
     * This filter replaces each pixel by the median of the input pixel and its eight neighbours. Each of the RGB channels is considered separately.
     * @param documentId
     * @param angle
     * @param distance
     * @param rotation
     * @param wrapEdges
     * @param zoom
     * @return
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    @ApiOperation(value = "This filter simulates motion blur on an image. You specify a combination of angle/distance for linear motion blur, a rotaiton angle for spin blur or a zoom factor for zoom blur. You can combine these in any proportions you want to get effects like spiral blurs.")
    @RequestMapping(value = "/{documentId}/motionblur", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<byte[]> imageMotionBlurByFile(
            @ApiParam(name = "documentId", required = true, defaultValue = "8D981024-A297-4169-8603-E503CC38EEDA") @PathVariable(value = "documentId") String documentId
            , @ApiParam(name="angle", required = true, defaultValue = "0")  @RequestParam(value="angle", required = false, defaultValue="0") float angle
            , @ApiParam(name="distance", required = true, defaultValue = "1")  @RequestParam(value="distance", required = false,  defaultValue="0") float distance
            , @ApiParam(name="rotation", required = true, defaultValue = "0")  @RequestParam(value="rotation", required = false,  defaultValue="0") float rotation
            , @ApiParam(name="wrapEdges", required = true, defaultValue = "false")  @RequestParam(value="wrapEdges", required = false,  defaultValue="false") boolean wrapEdges
            , @ApiParam(name="zoom", required = true, defaultValue = "0")  @RequestParam(value="zoom", required = false,  defaultValue="0") float zoom
    ) throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        MotionBlurJob args = new MotionBlurJob();
        args.setDocumentId(documentId);
        args.setAngle(angle);
        args.setDistance(distance);
        args.setRotation(rotation);
        args.setWrapEdges(wrapEdges);
        args.setZoom(zoom);


        Future<Map> imageFuture = imageFilterMotionBlurGateway.imageMotionBlurFilter(args);
        ImageDocumentJob payload = (ImageDocumentJob)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getBufferedImage();
        String contentType = payload.getDocument().getContentType().name();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage( bufferedImage, contentType, false );
        return result;
    }



    /**
     * This filter replaces each pixel by the median of the input pixel and its eight neighbours. Each of the RGB channels is considered separately.
     * @param file
     * @param angle
     * @param distance
     * @param rotation
     * @param wrapEdges
     * @param zoom
     * @return
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    @ApiOperation(value = "This filter simulates motion blur on an image. You specify a combination of angle/distance for linear motion blur, a rotaiton angle for spin blur or a zoom factor for zoom blur. You can combine these in any proportions you want to get effects like spiral blurs.")
    @RequestMapping(value = "/motionblur", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<byte[]> imageMotionBlurByFile(
            @ApiParam(name = "file", required = true) @RequestParam(value = "file", required = true) MultipartFile file
            , @ApiParam(name="angle", required = true, defaultValue = "0")  @RequestParam(value="angle", required = false, defaultValue="0") float angle
            , @ApiParam(name="distance", required = true, defaultValue = "1")  @RequestParam(value="distance", required = false,  defaultValue="0") float distance
            , @ApiParam(name="rotation", required = true, defaultValue = "0")  @RequestParam(value="rotation", required = false,  defaultValue="0") float rotation
            , @ApiParam(name="wrapEdges", required = true, defaultValue = "false")  @RequestParam(value="wrapEdges", required = false,  defaultValue="false") boolean wrapEdges
            , @ApiParam(name="zoom", required = true, defaultValue = "0")  @RequestParam(value="zoom", required = false,  defaultValue="0") float zoom
    ) throws TimeoutException, ExecutionException, InterruptedException, IOException
    {
        MotionBlurJob job = new MotionBlurJob();
        job.setDocumentId(null);
        job.setDocument( new Document(file) );
        job.getDocument().setContentType(MimeType.getMimeType(file.getContentType()) );
        job.getDocument().setFileName(file.getOriginalFilename());
        job.setAngle(angle);
        job.setDistance(distance);
        job.setRotation(rotation);
        job.setWrapEdges(wrapEdges);
        job.setZoom(zoom);


        Future<Map> imageFuture = imageFilterMotionBlurGateway.imageMotionBlurFilter(job);
        ImageDocumentJob payload = (ImageDocumentJob)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getBufferedImage();
        String contentType = payload.getDocument().getContentType().name();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage( bufferedImage, contentType, false );
        return result;
    }



}
