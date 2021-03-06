package apiserver.services.pdf.controllers;

import apiserver.core.common.ResponseEntityHelper;
import apiserver.core.connectors.coldfusion.services.BinaryJob;
import apiserver.services.cache.model.Document;
import apiserver.services.pdf.gateways.PdfGateway;
import apiserver.services.pdf.gateways.jobs.SecurePdfJob;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by mnimer on 4/18/14.
 */
@Controller
@RestController
@Api(value = "/pdf", description = "[PDF]")
@RequestMapping("/pdf")
public class TransformerController
{
    @Qualifier("transformPdfGateway")
    @Autowired
    public PdfGateway gateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;


    /**
     * Transform pages
     * @param file
     * @param password
     * @return
     * @throws InterruptedException
     * @throws java.util.concurrent.ExecutionException
     * @throws java.util.concurrent.TimeoutException
     * @throws java.io.IOException
     * @throws Exception
     */
    @ApiOperation(value = "Transform pages in pdf")
    @RequestMapping(value = "/transform", method = RequestMethod.POST, produces = "application/pdf")
    public ResponseEntity<byte[]> transformPdf(
            @ApiParam(name = "file", required = true) @RequestPart("file") MultipartFile file,
            @ApiParam(name = "password", required = false) @RequestPart("password") String password,
            @ApiParam(name = "hScale", required = false) @RequestPart("hScale") Double hScale,
            @ApiParam(name = "vScale", required = false) @RequestPart("vScale") Double vScale,
            @ApiParam(name = "position", required = false) @RequestPart("position") String position,
            @ApiParam(name = "rotation", required = false, allowableValues = "0, 90, 180, 270") @RequestPart("rotation") Integer rotation
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException, Exception
    {
        SecurePdfJob job = new SecurePdfJob();
        //file
        job.setFile(new Document(file));
        if( password != null ) job.setPassword(password);
        if( hScale != null ) job.setHScale(hScale);
        if( vScale != null ) job.setVScale(vScale);
        if( position != null ) job.setPosition(position);
        if( rotation != null ) job.setRotation(rotation);

        Future<Map> future = gateway.transformPdf(job);
        BinaryJob payload = (BinaryJob)future.get(defaultTimeout, TimeUnit.MILLISECONDS);

        byte[] fileBytes = payload.getPdfBytes();
        String contentType = "application/pdf";
        ResponseEntity<byte[]> result = ResponseEntityHelper.processFile(fileBytes, contentType, false);
        return result;
    }
}
