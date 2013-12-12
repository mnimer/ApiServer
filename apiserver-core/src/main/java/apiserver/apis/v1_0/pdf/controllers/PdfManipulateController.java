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
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/pdf-manipulate")
public class PdfManipulateController
{
    //@Autowired
    //public PdfConversionGateway pdfConversionGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;



    /**
     * Add Header to PDF pages
     * @param file
     * @param DDX
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws IOException
     * @throws Exception
     */
    @ApiOperation(value = "Add Header to PDF pages")
    @Produces("application/pdf")
    @RequestMapping(value = "/header", method = RequestMethod.POST)
    public ResponseEntity<byte[]> addHeader(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {
        throw new NotImplementedException();
        //http://help.adobe.com/en_US/ColdFusion/10.0/Developing/WS218B0C7A-5120-4583-A7BA-E0A2657F1362.html
    }
    /**
     * Add Header to cached PDF pages
     * @param file
     * @param DDX
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws IOException
     * @throws Exception
     */
    @ApiOperation(value = "Add Header to cached PDF pages")
    @Produces("application/pdf")
    @RequestMapping(value = "/{documentId}/header", method = RequestMethod.POST)
    public ResponseEntity<byte[]> addHeaderToCachedPdf(
            @ApiParam(name="documentId", required = true) @RequestPart("documentId") String documentId
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {
        throw new NotImplementedException();
        //http://help.adobe.com/en_US/ColdFusion/10.0/Developing/WS218B0C7A-5120-4583-A7BA-E0A2657F1362.html
    }


    /**
     * Add Footer to PDF pages
     * @param file
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws IOException
     * @throws Exception
     */
    @ApiOperation(value = "Add Footer to PDF pages")
    @Produces("application/pdf")
    @RequestMapping(value = "/footer", method = RequestMethod.POST)
    public ResponseEntity<byte[]> addFooter(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {
        throw new NotImplementedException();
        //http://help.adobe.com/en_US/ColdFusion/10.0/Developing/WS218B0C7A-5120-4583-A7BA-E0A2657F1362.html
    }

    /**
     * Add Footer to PDF pages
     * @param file
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws IOException
     * @throws Exception
     */
    @ApiOperation(value = "Add Footer to cached PDF pages")
    @Produces("application/pdf")
    @RequestMapping(value = "/{documentId}/footer", method = RequestMethod.POST)
    public ResponseEntity<byte[]> addFooterToCachedPdf(
            @ApiParam(name="documentId", required = true) @RequestPart("documentId") String documentId
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {
        throw new NotImplementedException();
        //http://help.adobe.com/en_US/ColdFusion/10.0/Developing/WS218B0C7A-5120-4583-A7BA-E0A2657F1362.html
    }


    /**
     * Apply a DDX file to a PDF for advanced manipulation
     * @param file
     * @param DDX
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws IOException
     * @throws Exception
     */
    @ApiOperation(value = "Use a DDX file for advanced manipulation")
    @Produces("application/pdf")
    @RequestMapping(value = "/ddx", method = RequestMethod.POST)
    public ResponseEntity<byte[]> processDDX(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file,
            @ApiParam(name="ddx", required = true) @RequestParam("ddx") String DDX
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {
        throw new NotImplementedException();
    }



    /**
     * apply a DDX file to a cached PDF for advanced manipulation
     * @param file
     * @param DDX
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws IOException
     * @throws Exception
     */
    @ApiOperation(value = "Use a DDX file for advanced manipulation")
    @Produces("application/pdf")
    @RequestMapping(value = "/{documentId}/ddx", method = RequestMethod.POST)
    public ResponseEntity<byte[]> processCachedPdfDDX(
            @ApiParam(name="documentId", required = true) @RequestPart("documentId") String documentId,
            @ApiParam(name="ddx", required = true) @RequestParam("ddx") String DDX
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {
        throw new NotImplementedException();
    }

}
