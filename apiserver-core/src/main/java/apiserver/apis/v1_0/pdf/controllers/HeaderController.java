package apiserver.apis.v1_0.pdf.controllers;

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
 * Created by mnimer on 4/16/14.
 */
@Controller
@Api(value = "/pdf", description = "[PDF]")
@RequestMapping("/pdf")
public class HeaderController
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
     * @throws java.util.concurrent.ExecutionException
     * @throws java.util.concurrent.TimeoutException
     * @throws java.io.IOException
     * @throws Exception
     */
    @ApiOperation(value = "Add Header to PDF pages")
    @Produces("application/pdf")
    @RequestMapping(value = "/modify/header", method = RequestMethod.POST)
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
    @RequestMapping(value = "/modify/{documentId}/header", method = RequestMethod.POST)
    public ResponseEntity<byte[]> addHeaderToCachedPdf(
            @ApiParam(name="documentId", required = true) @RequestPart("documentId") String documentId
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {
        throw new NotImplementedException();
        //http://help.adobe.com/en_US/ColdFusion/10.0/Developing/WS218B0C7A-5120-4583-A7BA-E0A2657F1362.html
    }

}
