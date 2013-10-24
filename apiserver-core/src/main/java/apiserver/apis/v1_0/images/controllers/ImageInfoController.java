package apiserver.apis.v1_0.images.controllers;

import apiserver.apis.v1_0.images.gateways.images.ImageInfoGateway;
import apiserver.apis.v1_0.images.gateways.images.ImageMetadataGateway;
import apiserver.apis.v1_0.images.gateways.jobs.images.FileInfoJob;
import apiserver.apis.v1_0.images.gateways.jobs.images.FileMetadataJob;
import apiserver.core.common.ResponseEntityHelper;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * User: mnimer
 * Date: 9/15/12
 */
@Controller
@Api(value = "/image/info", description = "[IMAGE]")
@RequestMapping("/image/info")
public class ImageInfoController
{

    @Autowired
    private ImageInfoGateway gateway;


    @Autowired
    public ImageMetadataGateway imageMetadataGateway;


    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;



    /**
     * get basic info
     * @param file
     * @return   height,width, pixel size, transparency
     * , @RequestPart("meta-data") Object metadata
     *
    , @RequestParam MultipartFile file

     */
    @ApiOperation(value = "Get the height and width for the image", responseClass = "java.util.Map")
    @RequestMapping(value = "/{documentId}/size", method = {RequestMethod.GET})
    public Callable<ResponseEntity<Map>> imageInfoByImage(
            @ApiParam(name = "documentId", required = true) @PathVariable(value = "documentId") String documentId
    )
    {
        final String _documentId = documentId;

        Callable<ResponseEntity<Map>> callable = new Callable<ResponseEntity<Map>>()
        {
            @Override
            public ResponseEntity<Map> call() throws Exception
            {
                FileInfoJob args = new FileInfoJob();
                args.setDocumentId(_documentId);

                Future<Map> imageFuture = gateway.imageInfo(args);
                Map payload = imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

                return (ResponseEntity)ResponseEntityHelper.processObject(payload);
            }
        };

        return callable;//new WebAsyncTask<Map>(10000, callable);
    }



    /**
     * get embedded metadata
     * @param file
     * @return   height,width, pixel size, transparency
     */
    @ApiOperation(value = "Get the embedded metadata", responseClass = "java.util.Map")
    @RequestMapping(value = "/{documentId}/metadata", method = {RequestMethod.GET})
    public Callable<Map> imageMetadataByImage(
            @ApiParam(name = "documentId", required = true) @PathVariable(value = "documentId") String documentId
    )
    {
        final String _documentId = documentId;

        Callable<Map> callable = new Callable<Map>()
        {
            @Override
            public Map call() throws Exception
            {
                FileMetadataJob args = new FileMetadataJob();
                args.setFile(_documentId);

                Future<Map> imageFuture = imageMetadataGateway.getMetadata(args);
                FileMetadataJob payload = (FileMetadataJob)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

                return payload.getMetadata();
            }
        };

        return callable;//new WebAsyncTask<Map>(10000, callable);
    }


}
