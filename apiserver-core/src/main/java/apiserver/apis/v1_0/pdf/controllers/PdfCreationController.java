package apiserver.apis.v1_0.pdf.controllers;

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
@RequestMapping("/pdf")
public class PdfCreationController
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
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws IOException
     */
    @ApiOperation(value = "Convert HTML into a PDF document")
    @Produces("application/pdf")
    @RequestMapping(value = "/generate/html", method = RequestMethod.POST)
    public ResponseEntity<byte[]> generatePdfWithHtmlString(
            @ApiParam(name="html", required = true) @RequestPart("html") String html,
            @ApiParam(name="relativeFiles", required = false) @RequestPart(value = "relativeFiles", required = false) MultipartFile[] relativeFiles
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
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





    @ApiOperation(value = "TODO")
    @Produces("application/pdf")
    @RequestMapping(value = "/merge", method = RequestMethod.POST)
    public ResponseEntity<byte[]> mergePdfDocuments(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile[] file
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        throw new NotImplementedException();
        // add image to pdf as watermark
        // http://help.adobe.com/en_US/ColdFusion/10.0/Developing/WSc3ff6d0ea77859461172e0811cbec11e6d-7fff.html
    }





    @ApiOperation(value = "TODO")
    @Produces("application/pdf")
    @RequestMapping(value = "/delete/pages", method = RequestMethod.POST)
    public ResponseEntity<byte[]> deletePagesFromPdf(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        throw new NotImplementedException();
        // add image to pdf as watermark
        // http://help.adobe.com/en_US/ColdFusion/10.0/Developing/WSc3ff6d0ea77859461172e0811cbec11e6d-7fff.html
    }





    @ApiOperation(value = "TODO")
    @Produces("application/pdf")
    @RequestMapping(value = "/thumbnails", method = RequestMethod.POST)
    public ResponseEntity<byte[]> thumbnailPdf(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        throw new NotImplementedException();
        // add image to pdf as watermark
        // http://help.adobe.com/en_US/ColdFusion/10.0/Developing/WSc3ff6d0ea77859461172e0811cbec11e6d-7fff.html
    }

}
