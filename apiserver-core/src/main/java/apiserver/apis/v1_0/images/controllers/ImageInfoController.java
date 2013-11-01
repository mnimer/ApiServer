package apiserver.apis.v1_0.images.controllers;

import apiserver.apis.v1_0.documents.model.Document;
import apiserver.apis.v1_0.images.gateways.images.ImageInfoGateway;
import apiserver.apis.v1_0.images.gateways.images.ImageMetadataGateway;
import apiserver.apis.v1_0.images.gateways.jobs.images.FileInfoJob;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.multipart.MultipartFile;

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
    @RequestMapping(value = "/{documentId}/size", method = {RequestMethod.GET})
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
     * get basic info about image.
     * @param file
     * @return
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws InterruptedException
     */
    @ApiOperation(value = "Get the height and width for the image", responseClass = "java.util.Map")
    @RequestMapping(value = "/size", method = {RequestMethod.POST})
    public WebAsyncTask<Map> imageInfoByImageAsync(
            @ApiParam(name = "file", required = true) @RequestParam(value = "file", required = true) MultipartFile file
    ) throws ExecutionException, TimeoutException, InterruptedException
    {
        final MultipartFile _file = file;

        Callable<Map> callable = new Callable<Map>()
        {
            @Override
            public Map call() throws Exception
            {
                FileInfoJob job = new FileInfoJob();
                job.setDocumentId(null);
                job.setDocument( new Document(_file) );
                job.getDocument().setContentType( _file.getContentType() );
                job.getDocument().setFileName(_file.getOriginalFilename());


                Future<Map> imageFuture = gateway.imageInfo(job);
                Map payload = imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

                return payload;
            }
        };

        return new WebAsyncTask<>(defaultTimeout, callable);
    }


}
