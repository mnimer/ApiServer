package apiserver.apis.v1_0.images.controllers;

import apiserver.apis.v1_0.images.gateways.images.ImageInfoGateway;
import apiserver.apis.v1_0.images.gateways.images.ImageMetadataGateway;
import apiserver.apis.v1_0.images.models.images.FileInfoModel;
import apiserver.apis.v1_0.images.models.images.FileMetadataModel;
import apiserver.core.common.ResponseEntityHelper;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
    @RequestMapping(value = "/size", method = {RequestMethod.POST})
    public Callable<ResponseEntity<Map>> imageInfoByImage(
            @ApiParam(value = "uploaded file to process", required = true) @RequestParam("file") MultipartFile file)
    {
        final MultipartFile _file = file;

        Callable<ResponseEntity<Map>> callable = new Callable<ResponseEntity<Map>>()
        {
            @Override
            public ResponseEntity<Map> call() throws Exception
            {
                FileInfoModel args = new FileInfoModel();
                args.setFile(_file);

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
    @RequestMapping(value = "/metadata", method = {RequestMethod.POST})
    public Callable<Map> imageMetadataByImage(
            @ApiParam(name="file", required = true) @RequestParam("file") MultipartFile file )
    {
        final MultipartFile _file = file;

        Callable<Map> callable = new Callable<Map>()
        {
            @Override
            public Map call() throws Exception
            {
                FileMetadataModel args = new FileMetadataModel();
                args.setFile(_file);

                Future<Map> imageFuture = imageMetadataGateway.getMetadata(args);
                FileMetadataModel payload = (FileMetadataModel)imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

                return payload.getMetadata();
            }
        };

        return callable;//new WebAsyncTask<Map>(10000, callable);
    }


}
