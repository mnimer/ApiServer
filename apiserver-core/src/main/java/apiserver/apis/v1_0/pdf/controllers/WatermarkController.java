package apiserver.apis.v1_0.pdf.controllers;

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

import apiserver.exceptions.NotImplementedException;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.Produces;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * User: mnimer
 * Date: 9/15/12
 */
@Controller
@Api(value = "/pdf", description = "[PDF]")
@RequestMapping("/pdf")
public class WatermarkController
{
    //@Autowired
    //public PdfConversionGateway pdfConversionGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;


    /**
     * Add Watermark
     * @param file
     * @param image
     * @param foreground
     * @param showOnPrint
     * @param position
     * @param opacity
     * @return
     * @throws InterruptedException
     * @throws java.util.concurrent.ExecutionException
     * @throws java.util.concurrent.TimeoutException
     * @throws java.io.IOException
     * @throws Exception
     */
    @ApiOperation(value = "Add Watermark")
    @Produces("application/pdf")
    @RequestMapping(value = "/modify/watermark", method = RequestMethod.POST)
    public ResponseEntity<byte[]> addWatermarkToPdf(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file,
            @ApiParam(name="image", required = true) @RequestPart("image") MultipartFile image,
            @ApiParam(name="foreground", required = false) @RequestParam(value="foreground") Boolean foreground,
            @ApiParam(name="showOnPrint", required = false) @RequestParam(value="showOnPrint") Boolean showOnPrint,
            @ApiParam(name="position", required = false) @RequestParam(value="position") String position,
            @ApiParam(name="opacity", required = false) @RequestParam(value="opacity") double opacity
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {
        throw new NotImplementedException();

    }

    /**
     * Add Watermark to cached pdf
     * @param documentId
     * @param image
     * @param foreground
     * @param showOnPrint
     * @param position
     * @param opacity
     * @return
     * @throws InterruptedException
     * @throws java.util.concurrent.ExecutionException
     * @throws java.util.concurrent.TimeoutException
     * @throws java.io.IOException
     * @throws Exception
     */
    @ApiOperation(value = "Add Watermark to cached pdf")
    @Produces("application/pdf")
    @RequestMapping(value = "/modify/{documentId}/watermark", method = RequestMethod.POST)
    public ResponseEntity<byte[]> addWatermarkToPdf(
            @ApiParam(name="documentId", required = true) @RequestPart("documentId") String documentId,
            @ApiParam(name="image", required = true) @RequestPart("image") MultipartFile image,
            @ApiParam(name="foreground", required = false) @RequestParam(value="foreground") Boolean foreground,
            @ApiParam(name="showOnPrint", required = false) @RequestParam(value="showOnPrint") Boolean showOnPrint,
            @ApiParam(name="position", required = false) @RequestParam(value="position") String position,
            @ApiParam(name="opacity", required = false) @RequestParam(value="opacity") double opacity
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {
        throw new NotImplementedException();

    }


    /**
     * Remove watermark
     * @param file
     * @return
     * @throws InterruptedException
     * @throws java.util.concurrent.ExecutionException
     * @throws java.util.concurrent.TimeoutException
     * @throws java.io.IOException
     * @throws Exception
     */
    @ApiOperation(value = "Remove Watermark")
    @Produces("application/pdf")
    @RequestMapping(value = "/modify/watermark", method = RequestMethod.DELETE)
    public ResponseEntity<byte[]> removeWatermarkFromPdf(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {
        throw new NotImplementedException();

    }


}
