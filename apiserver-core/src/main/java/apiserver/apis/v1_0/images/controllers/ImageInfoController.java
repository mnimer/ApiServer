package apiserver.apis.v1_0.images.controllers;

import apiserver.apis.v1_0.images.gateways.images.ImageInfoGateway;
import apiserver.apis.v1_0.images.gateways.images.ImageMetadataGateway;
import apiserver.apis.v1_0.images.gateways.jobs.images.FileInfoJob;
import apiserver.apis.v1_0.images.gateways.jobs.images.FileMetadataJob;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.Map;
import java.util.concurrent.*;

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
     * get basic info about image.
     * @param documentId cache id
     * @return   height,width, pixel size, transparency
     * , @RequestPart("meta-data") Object metadata
     *
    , @RequestParam MultipartFile file

     */
    @ApiOperation(value = "Get the height and width for the image", responseClass = "java.util.Map")
    @RequestMapping(value = "/{documentId}/size.{format}", method = {RequestMethod.GET})
    public WebAsyncTask<Map> imageInfoByImageAsync(
            @ApiParam(name = "documentId", required = true, defaultValue = "8D981024-A297-4169-8603-E503CC38EEDA")
            @PathVariable(value = "documentId") String documentId
    ) throws ExecutionException, TimeoutException, InterruptedException
    {
        final String _documentId = documentId;

        Callable<Map> callable = new Callable<Map>()
        {
            @Override
            public Map call() throws Exception
            {
                FileInfoJob args = new FileInfoJob();
                args.setDocumentId(_documentId);

                Future<Map> imageFuture = gateway.imageInfo(args);
                Map payload = imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

                return payload;
            }
        };

        return new WebAsyncTask<>(defaultTimeout, callable);
    }



    /**
     * get embedded metadata
     * @param documentId
     * @return   height,width
     */
    @ApiOperation(value = "Get the embedded metadata", responseClass = "java.util.Map")
    @RequestMapping(value = "/{documentId}/metadata", method = {RequestMethod.GET})
    public WebAsyncTask<Map> imageMetadataByImage(
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

        return new WebAsyncTask<Map>(defaultTimeout, callable);
    }


}
