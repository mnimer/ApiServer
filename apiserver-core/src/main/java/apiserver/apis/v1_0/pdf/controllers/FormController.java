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
import apiserver.apis.v1_0.pdf.gateways.PdfFormGateway;
import apiserver.apis.v1_0.pdf.gateways.jobs.ExtractPdfFormJob;
import apiserver.apis.v1_0.pdf.gateways.jobs.PopulatePdfFormJob;
import apiserver.core.common.ResponseEntityHelper;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.Produces;
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
public class FormController
{
    @Autowired
    public PdfFormGateway gateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;


    /**
     * Extract the value of the form fields in a pdf
     * @param file
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws IOException
     * @throws Exception
     */
    @ApiOperation(value = "Extract the value of the form fields in a pdf")
    @Produces("application/pdf")
    @RequestMapping(value = "/form/extract", method = RequestMethod.POST)
    public ResponseEntity<Object> extractFormFields(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {
        ExtractPdfFormJob job = new ExtractPdfFormJob();
        job.setFile(new Document(file));
        job.getFile().setContentType( MimeType.getMimeType(file.getContentType()) );
        job.getFile().setFileName( file.getOriginalFilename() );

        Future<Map> future = gateway.extractPdfForm(job);
        ExtractPdfFormJob payload = (ExtractPdfFormJob)future.get(defaultTimeout, TimeUnit.MILLISECONDS);

        return ResponseEntityHelper.processObject(payload);
    }


    /**
     * Extract the value of the form fields in a pdf
     * @param documentId
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws IOException
     * @throws Exception
     */
    @ApiOperation(value = "Extract the value of the form fields in a pdf")
    @Produces("application/pdf")
    @RequestMapping(value = "/form/{documentId}/extract", method = RequestMethod.GET)
    public ResponseEntity<Object> extractCachedFormFields(
            @ApiParam(name="documentId", required = true) @RequestPart("documentId") String documentId
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {
        ExtractPdfFormJob job = new ExtractPdfFormJob();
        job.setDocumentId(documentId);

        Future<Map> future = gateway.extractPdfForm(job);
        ExtractPdfFormJob payload = (ExtractPdfFormJob)future.get(defaultTimeout, TimeUnit.MILLISECONDS);

        return ResponseEntityHelper.processObject(payload);
    }


    /**
     * Populate the pdf form fields
     * @param file
     * @param xfdf XML
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws IOException
     * @throws Exception
     */
    @ApiOperation(value = "Populate the pdf form fields")
    @Produces("application/pdf")
    @RequestMapping(value = "/form/populate", method = RequestMethod.POST)
    public ResponseEntity<byte[]> populateFormFields(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file,
            @ApiParam(name="XFDF", required = true) @RequestPart("XFDF") String xfdf
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {
        PopulatePdfFormJob job = new PopulatePdfFormJob();
        job.setFile(new Document(file));
        job.getFile().setContentType( MimeType.getMimeType(file.getContentType()) );
        job.getFile().setFileName( file.getOriginalFilename() );
        job.setXFDF(xfdf);

        Future<Map> future = gateway.populatePdfForm(job);
        PopulatePdfFormJob payload = (PopulatePdfFormJob)future.get(defaultTimeout, TimeUnit.MILLISECONDS);

        byte[] fileBytes = payload.getPdfBytes();
        String contentType = "application/pdf";
        ResponseEntity<byte[]> result = ResponseEntityHelper.processFile(fileBytes, contentType, false);
        return result;
    }


    /**
     * Populate the pdf form fields
     * @param documentId
     * @param xfdf XML
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws IOException
     * @throws Exception
     */
    @ApiOperation(value = "Populate the pdf form fields")
    @Produces("application/pdf")
    @RequestMapping(value = "/form/{documentId}/populate", method = RequestMethod.POST)
    public ResponseEntity<byte[]> populateCachedFormFields(
            @ApiParam(name="documentId", required = true) @RequestPart("documentId") String documentId,
            @ApiParam(name="XFDF", required = true) @RequestPart("XFDF") String xfdf
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {
        PopulatePdfFormJob job = new PopulatePdfFormJob();
        job.setDocumentId(documentId);
        job.setXFDF(xfdf);

        Future<Map> future = gateway.populatePdfForm(job);
        PopulatePdfFormJob payload = (PopulatePdfFormJob)future.get(defaultTimeout, TimeUnit.MILLISECONDS);

        byte[] fileBytes = payload.getPdfBytes();
        String contentType = "application/pdf";
        ResponseEntity<byte[]> result = ResponseEntityHelper.processFile(fileBytes, contentType, false);
        return result;
    }


}
