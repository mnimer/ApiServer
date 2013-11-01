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
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
@RequestMapping("/pdf/manipulate")
public class PdfInfoController
{
    //@Autowired
    //public PdfHtmlGateway pdfHtmlGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;


    @ApiOperation(value = "TODO")
    @Produces("application/pdf")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public ResponseEntity<byte[]> getPdfInfo(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        throw new NotImplementedException();
        //
    }



    @ApiOperation(value = "TODO")
    @Produces("application/pdf")
    @RequestMapping(value = "/info", method = RequestMethod.PUT)
    public ResponseEntity<byte[]> setPdfInfo(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        throw new NotImplementedException();
        //
        //
    }



    @ApiOperation(value = "TODO")
    @Produces("application/pdf")
    @RequestMapping(value = "/protect", method = RequestMethod.POST)
    public ResponseEntity<byte[]> protectPdf(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile[] file
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        throw new NotImplementedException();

    }



    @ApiOperation(value = "TODO")
    @Produces("application/pdf")
    @RequestMapping(value = "/optimize", method = RequestMethod.POST)
    public ResponseEntity<byte[]> optimizePdf(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        throw new NotImplementedException();
        //
    }



    @ApiOperation(value = "TODO")
    @Produces("application/pdf")
    @RequestMapping(value = "/extract/text", method = RequestMethod.POST)
    public ResponseEntity<byte[]> extractTextFromPdf(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        throw new NotImplementedException();
        //http://help.adobe.com/en_US/ColdFusion/10.0/Developing/WS79586299-88D8-4301-A5A9-78123AE16480.html
    }




    @ApiOperation(value = "TODO")
    @Produces("application/pdf")
    @RequestMapping(value = "/extract/image", method = RequestMethod.POST)
    public ResponseEntity<byte[]> extractImageFromPdf(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        throw new NotImplementedException();
        //http://help.adobe.com/en_US/ColdFusion/10.0/Developing/WS79586299-88D8-4301-A5A9-78123AE16480.html
    }



    /**
     *
     For efficient access of PDF files over the web, linearize PDF documents.
     A linearized PDF file is structured in a way that displays the first page of the PDF file in the browser
     before the entire file is downloaded from the web server. As a result linear PDF documents open almost instantly.
     * @param file
     * @return
     * @throws InterruptedException
     * @throws java.util.concurrent.ExecutionException
     * @throws java.util.concurrent.TimeoutException
     * @throws java.io.IOException
     */
    @ApiOperation(value = "For efficient access of PDF files over the web, linearize PDF documents. A linearized PDF file is structured in a way that displays the first page of the PDF file in the browser before the entire file is downloaded from the web server. As a result linear PDF documents open almost instantly.")
    @Produces("application/pdf")
    @RequestMapping(value = "/linearize", method = RequestMethod.POST)
    public ResponseEntity<byte[]> processDDX(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        throw new NotImplementedException();

    }

}
