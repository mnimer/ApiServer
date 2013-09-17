package apiserver.apis.v1_0.pdf.controllers;

import apiserver.apis.v1_0.common.ResponseEntityHelper;
import apiserver.apis.v1_0.pdf.gateways.pdf.PdfHtmlGateway;
import apiserver.apis.v1_0.pdf.models.PdfHtmlModel;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;

/**
 * User: mnimer
 * Date: 9/15/12
 */
@Controller
@RequestMapping("/pdf")
public class PdfHtmlController
{
    @Autowired
    public PdfHtmlGateway pdfHtmlGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;




    @ResponseBody
    @ApiOperation(value = "Convert HTML into a PDF document")
    @RequestMapping(value = "/generate/html/{cacheId}/", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> generatePdfById(
            @ApiParam(value = "cache id returned by /pdf/cache/add", required = true, defaultValue = "") @PathVariable("cacheId") String cacheId
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        PdfHtmlModel args = new PdfHtmlModel();
        args.setCacheId(cacheId);

        Future<Map> future = pdfHtmlGateway.convertHtmlToPdf(args);
        PdfHtmlModel payload = (PdfHtmlModel)future.get(defaultTimeout, TimeUnit.MILLISECONDS);

        byte[] file = payload.getProcessedFile();
        String contentType = payload.getContentType();

        ResponseEntity<byte[]> result = ResponseEntityHelper.processFile(file, contentType, returnAsBase64);
        return result;
    }



    @ResponseBody
    @ApiOperation(value = "Convert HTML into a PDF document")
    @RequestMapping(value = "/generate/html", method = {RequestMethod.POST})
    public ResponseEntity<byte[]> generatePdfByHtml(
            @ApiParam(name="html", required = true) @RequestPart("html") String html
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        PdfHtmlModel args = new PdfHtmlModel();
        args.setHtml(html);

        Future<Map> future = pdfHtmlGateway.convertHtmlToPdf(args);
        PdfHtmlModel payload = (PdfHtmlModel)future.get(defaultTimeout, TimeUnit.MILLISECONDS);

        byte[] file = payload.getProcessedFile();
        String contentType = payload.getContentType();

        ResponseEntity<byte[]> result = ResponseEntityHelper.processFile(file, contentType, returnAsBase64);
        return result;
    }
}
