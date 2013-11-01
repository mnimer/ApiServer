package apiserver.apis.v1_0.pdf.controllers;

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
public class PdfManipulateController
{
    //@Autowired
    //public PdfHtmlGateway pdfHtmlGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;


    @ApiOperation(value = "TODO")
    @Produces("application/pdf")
    @RequestMapping(value = "/watermark", method = RequestMethod.POST)
    public ResponseEntity<byte[]> addWatermarkToPdf(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file,
            @ApiParam(name="image", required = true) @RequestPart("image") MultipartFile image,
            @ApiParam(name="foreground", required = false) @RequestParam(value="foreground") Boolean foreground,
            @ApiParam(name="showOnPrint", required = false) @RequestParam(value="showOnPrint") Boolean showOnPrint,
            @ApiParam(name="position", required = false) @RequestParam(value="position") String position,
            @ApiParam(name="opacity", required = false) @RequestParam(value="opacity") double opacity
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        throw new NotImplementedException();

    }




    @ApiOperation(value = "TODO")
    @Produces("application/pdf")
    @RequestMapping(value = "/watermark/remove", method = RequestMethod.POST)
    public ResponseEntity<byte[]> removeWatermarkFromPdf(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        throw new NotImplementedException();

    }




    @ApiOperation(value = "TODO")
    @Produces("application/pdf")
    @RequestMapping(value = "/transform", method = RequestMethod.POST)
    public ResponseEntity<byte[]> transformPdf(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        throw new NotImplementedException();

    }



    @ApiOperation(value = "TODO")
    @Produces("application/pdf")
    @RequestMapping(value = "/addHeader", method = RequestMethod.POST)
    public ResponseEntity<byte[]> addHeader(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file,
            @ApiParam(name="ddx", required = true) @RequestParam("ddx") String DDX
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        throw new NotImplementedException();
        //http://help.adobe.com/en_US/ColdFusion/10.0/Developing/WS218B0C7A-5120-4583-A7BA-E0A2657F1362.html
    }




    @ApiOperation(value = "TODO")
    @Produces("application/pdf")
    @RequestMapping(value = "/addFooter", method = RequestMethod.POST)
    public ResponseEntity<byte[]> addFooter(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        throw new NotImplementedException();
        //http://help.adobe.com/en_US/ColdFusion/10.0/Developing/WS218B0C7A-5120-4583-A7BA-E0A2657F1362.html
    }




    @ApiOperation(value = "TODO")
    @Produces("application/pdf")
    @RequestMapping(value = "/ddx", method = RequestMethod.POST)
    public ResponseEntity<byte[]> processDDX(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file,
            @ApiParam(name="ddx", required = true) @RequestParam("ddx") String DDX
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        throw new NotImplementedException();

    }

}
