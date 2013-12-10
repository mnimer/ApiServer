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
import apiserver.exceptions.NotImplementedException;

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
@RequestMapping("/pdf-form")
public class FormController
{
    //@Autowired
    //public PdfHtmlGateway pdfHtmlGateway;

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
    @RequestMapping(value = "/extract", method = RequestMethod.POST)
    public ResponseEntity<byte[]> extractFormFields(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {
        throw new NotImplementedException();
        //Take existing PDF and populate form fields with XML data
        // http://help.adobe.com/en_US/ColdFusion/10.0/Developing/WSc3ff6d0ea77859461172e0811cbec11c2b-7ffa.html

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
    @RequestMapping(value = "/{documentId}/extract", method = RequestMethod.GET)
    public ResponseEntity<byte[]> extractCachedFormFields(
            @ApiParam(name="documentId", required = true) @RequestPart("documentId") String documentId
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {
        throw new NotImplementedException();
        //Take existing PDF and populate form fields with XML data
        // http://help.adobe.com/en_US/ColdFusion/10.0/Developing/WSc3ff6d0ea77859461172e0811cbec11c2b-7ffa.html

    }


    /**
     * Populate the pdf form fields
     * @param file
     * @param XFDF
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws IOException
     * @throws Exception
     */
    @ApiOperation(value = "Populate the pdf form fields")
    @Produces("application/pdf")
    @RequestMapping(value = "/populate", method = RequestMethod.POST)
    public ResponseEntity<byte[]> populateFormFields(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file,
            @ApiParam(name="XFDF", required = true) @RequestPart("XFDF") String XFDF
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {
        throw new NotImplementedException();
        //Take existing PDF and populate form fields with XML data
        // http://help.adobe.com/en_US/ColdFusion/10.0/Developing/WSc3ff6d0ea77859461172e0811cbec22c24-7994.html

    }


    /**
     * Populate the pdf form fields
     * @param documentId
     * @param XFDF
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws IOException
     * @throws Exception
     */
    @ApiOperation(value = "Populate the pdf form fields")
    @Produces("application/pdf")
    @RequestMapping(value = "/{documentId}/populate", method = RequestMethod.POST)
    public ResponseEntity<byte[]> populateCachedFormFields(
            @ApiParam(name="documentId", required = true) @RequestPart("documentId") String documentId,
            @ApiParam(name="XFDF", required = true) @RequestPart("XFDF") String XFDF
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {
        throw new NotImplementedException();
        //Take existing PDF and populate form fields with XML data
        // http://help.adobe.com/en_US/ColdFusion/10.0/Developing/WSc3ff6d0ea77859461172e0811cbec22c24-7994.html

    }


}
