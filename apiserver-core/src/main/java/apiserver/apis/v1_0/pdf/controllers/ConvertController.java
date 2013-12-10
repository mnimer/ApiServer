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
@RequestMapping("/pdf-convert")
public class ConvertController
{
    //@Autowired
    //public PdfHtmlGateway pdfHtmlGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;


    /**
     * Convert an HTML string into a PDF document.
     * @param html String of html to generate into a pdf
     * @param relativeFiles multiple files that are referenced in the html to generate the html (css, js, etc.)
     * @return
     * @throws InterruptedException
     * @throws java.util.concurrent.ExecutionException
     * @throws java.util.concurrent.TimeoutException
     * @throws java.io.IOException
     */
    @ApiOperation(value = "Convert an HTML string into a PDF document.")
    @Produces("application/pdf")
    @RequestMapping(value = "/html", method = RequestMethod.POST)
    public ResponseEntity<byte[]> html2pdf(
            @ApiParam(name="html", required = true) @RequestPart("html") String html,
            @ApiParam(name="relativeFiles", required = false) @RequestPart(value = "relativeFiles", required = false) MultipartFile[] relativeFiles
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {
        throw new NotImplementedException();
        /**
        PdfHtmlJob args = new PdfHtmlJob();
        args.setHtml(html);

        Future<Map> future = null;//pdfHtmlGateway.convertHtmlToPdf(args);
        PdfHtmlJob payload = (PdfHtmlJob)future.get(defaultTimeout, TimeUnit.MILLISECONDS);

        byte[] fileBytes = payload.getDocument().getFileBytes();
        String contentType = "application/pdf";
        ResponseEntity<byte[]> result = ResponseEntityHelper.processFile(fileBytes, contentType, false);
        return result;
         **/
    }


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
    @RequestMapping(value = "/ppt", method = RequestMethod.POST)
    public ResponseEntity<byte[]> ppt2pdf(
            @ApiParam(name="pptFile", required = true) @RequestPart("pptFile") MultipartFile file
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {
        throw new NotImplementedException();
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
    @RequestMapping(value = "/{documentId}/ppt", method = RequestMethod.GET)
    public ResponseEntity<byte[]> cachedPpt2pdf(
            @ApiParam(name="documentId", required = true) @RequestPart("documentId") String documentId
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {
        throw new NotImplementedException();
    }



    /**
     * Convert an WORD file into a PDF document.
     * @param file String of html to generate into a pdf
     * @return
     * @throws InterruptedException
     * @throws java.util.concurrent.ExecutionException
     * @throws java.util.concurrent.TimeoutException
     * @throws java.io.IOException
     */
    @ApiOperation(value = "Convert an WORD file into a PDF document.")
    @Produces("application/pdf")
    @RequestMapping(value = "/word", method = RequestMethod.POST)
    public ResponseEntity<byte[]> word2pdf(
            @ApiParam(name="wordFile", required = true) @RequestPart("wordFile") MultipartFile file
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {
        throw new NotImplementedException();
    }



    /**
     * Convert an cached WORD file into a PDF document.
     * @param file
     * @return
     * @throws InterruptedException
     * @throws java.util.concurrent.ExecutionException
     * @throws java.util.concurrent.TimeoutException
     * @throws java.io.IOException
     */
    @ApiOperation(value = "Convert an cached WORD file into a PDF document.")
    @Produces("application/pdf")
    @RequestMapping(value = "/{documentId}/word", method = RequestMethod.GET)
    public ResponseEntity<byte[]> cachedWord2pdf(
            @ApiParam(name="wordFile", required = true) @RequestPart("wordFile") MultipartFile file
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {
        throw new NotImplementedException();
    }


}
