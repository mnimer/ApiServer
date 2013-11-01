package apiserver.apis.v1_0.documents.controllers;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * User: mikenimer
 * Date: 10/30/13
 */
@Controller
@RequestMapping("/office")
public class ConversionController
{
    private final Logger log = LoggerFactory.getLogger(ConversionController.class);



    @ApiOperation(value = "TODO")
    @Produces("application/octet-stream")
    @RequestMapping(value = "/conversion/ppt2pdf", method = RequestMethod.POST)
    public ResponseEntity<byte[]> ppt2pdf(
            @ApiParam(name="file", required = true) @RequestPart("file") MultipartFile file
    ) throws InterruptedException, ExecutionException, TimeoutException, IOException
    {
        throw new NotImplementedException();
    }

}
