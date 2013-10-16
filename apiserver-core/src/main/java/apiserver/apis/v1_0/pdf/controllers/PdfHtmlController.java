package apiserver.apis.v1_0.pdf.controllers;

import apiserver.apis.v1_0.pdf.gateways.pdf.PdfHtmlGateway;
import apiserver.apis.v1_0.pdf.models.PdfHtmlModel;
import apiserver.core.common.ResponseEntityHelper;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/pdf")
public class PdfHtmlController
{
    //@Autowired
    //public PdfHtmlGateway pdfHtmlGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;


    /**
     * Convert an HTML string into a PDF document.
     * @param html
     * @param returnAsBase64
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws IOException
     */
    @ResponseBody
    @ApiOperation(value = "Convert HTML into a PDF document")
    @Produces("application/pdf")
    @RequestMapping(value = "/generate/html", method = RequestMethod.POST)
    public ResponseEntity<byte[]> generatePdfByHtml(
            @ApiParam(name="html", required = true) @RequestPart("html") String html
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        PdfHtmlModel args = new PdfHtmlModel();
        args.setHtml(html);

        Future<Map> future = null;//pdfHtmlGateway.convertHtmlToPdf(args);
        PdfHtmlModel payload = (PdfHtmlModel)future.get(defaultTimeout, TimeUnit.MILLISECONDS);

        byte[] file = payload.getProcessedFileBytes();
        String contentType = "application/pdf";//payload.getContentType();


        ///response.setHeader( "Content-Disposition", "attachment;filename=" + filename );
        //response.setContentType("application/pdf");
        /**
         * HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.parseMediaType("application/pdf"));
         String filename = "output.pdf";
         headers.setContentDispositionFormData(filename, filename);
         headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
         ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);

         */
        ResponseEntity<byte[]> result = ResponseEntityHelper.processFile(file, contentType, returnAsBase64);
        return result;
    }
}
