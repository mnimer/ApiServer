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

import apiserver.apis.v1_0.MimeType;
import apiserver.apis.v1_0.documents.model.Document;
import apiserver.apis.v1_0.pdf.gateways.PdfConversionGateway;
import apiserver.apis.v1_0.pdf.gateways.jobs.Document2PdfJob;
import apiserver.core.common.ResponseEntityHelper;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.Produces;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * User: mnimer
 * Date: 9/15/12
 */
@Controller
@Api(value = "/pdf", description = "[PDF]")
@RequestMapping("/pdf")
public class ConvertDocumentController
{
    private final Logger log = LoggerFactory.getLogger(ConvertDocumentController.class);

    @Qualifier("convertPptToPdfApiGateway")
    @Autowired
    public PdfConversionGateway pdfPptGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;

    /**
     * Convert an PPT file into a PDF document.
     * @param file String of html to generate into a pdf
     * @return
     * @throws InterruptedException
     * @throws java.util.concurrent.ExecutionException
     * @throws java.util.concurrent.TimeoutException
     * @throws java.io.IOException
     */
    @ApiOperation(value = "Convert an PPT file into a PDF document.")
    @Produces("application/pdf")
    @RequestMapping(value = "/convert/document", method = RequestMethod.POST)
    public ResponseEntity<byte[]> document2pdf(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {

        Document2PdfJob job = new Document2PdfJob();
        job.setDocumentId(null);
        //job.setFile("/Users/mnimer/Downloads/First user test.ppt");
        job.setFile(new Document(file));
        job.getFile().setContentType(MimeType.getMimeType(file.getContentType()) );
        job.getFile().setFileName(file.getOriginalFilename());


        Future<Map> future = pdfPptGateway.convertDocumentToPdf(job);
        Document2PdfJob payload = (Document2PdfJob)future.get(defaultTimeout, TimeUnit.MILLISECONDS);


        byte[] fileBytes = payload.getPdfBytes();
        String contentType = MimeType.pdf.contentType;
        ResponseEntity<byte[]> result = ResponseEntityHelper.processFile(fileBytes, contentType, false);
        return result;
    }


    /**
     * Convert a cached PPT file into a PDF document.
     * @param documentId
     * @return
     * @throws InterruptedException
     * @throws java.util.concurrent.ExecutionException
     * @throws java.util.concurrent.TimeoutException
     * @throws java.io.IOException
     */
    @ApiOperation(value = "Convert an cached PPT file into a PDF document.")
    @Produces("application/pdf")
    @RequestMapping(value = "/convert/{documentId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> cachedPpt2pdf(
            @ApiParam(name="documentId", required = true) @RequestPart("documentId") String documentId
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {
        Document2PdfJob job = new Document2PdfJob();
        job.setDocumentId(documentId);

        Future<Map> future = pdfPptGateway.convertDocumentToPdf(job);
        Document2PdfJob payload = (Document2PdfJob)future.get(defaultTimeout, TimeUnit.MILLISECONDS);

        byte[] fileBytes = payload.getPdfBytes();
        String contentType = "application/pdf";
        ResponseEntity<byte[]> result = ResponseEntityHelper.processFile(fileBytes, contentType, false);
        return result;

    }




}
