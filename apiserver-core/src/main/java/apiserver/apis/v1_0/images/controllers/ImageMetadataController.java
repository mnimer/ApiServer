package apiserver.apis.v1_0.images.controllers;

import apiserver.apis.v1_0.images.ImageConfigMBean;
import apiserver.apis.v1_0.images.gateways.images.ImageMetadataGateway;
import apiserver.apis.v1_0.images.models.ImageModel;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

//todo add setMetadata(String, String) method
//todo add setMetadata(Map) method
//todo: STRIP METADATA
/**
 * User: mnimer
 * Date: 10/21/12
 */
//Controller
@RequestMapping("/image-metadata")
public class ImageMetadataController
{
    @Autowired
    private ImageConfigMBean imageConfigMBean;

    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    public ImageMetadataGateway imageMetadataGateway;



    public MessageChannel imageMetadataStripMetadataInputChannel;


    /**
     * get embedded metadata
     * @param cacheId - any valid URL or cache ID
     * @return  height,width, pixel size, transparency
     */
    @ApiOperation(value = "Get the embedded metadata", responseClass = "java.util.Map")
    @RequestMapping(value = "/{cacheId}/metadata", method =  {RequestMethod.GET})
    public Callable<Map> imageMetadataById(
            @ApiParam(name="cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52") @PathVariable("cacheId") String cacheId)
    {
        final String _cacheId = cacheId;

        Callable<Map> callable = new Callable<Map>()
        {
            @Override
            public Map call() throws Exception
            {
                ImageModel args = new ImageModel();
                args.setCacheId(_cacheId);

                Future<Map> imageFuture = imageMetadataGateway.getMetadata(args);
                Map payload = imageFuture.get(10000, TimeUnit.MILLISECONDS);

                return payload;
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
                ImageModel args = new ImageModel();
                args.setFile(_file);

                Future<Map> imageFuture = imageMetadataGateway.getMetadata(args);
                Map payload = imageFuture.get(10000, TimeUnit.MILLISECONDS);

                return payload;
            }
        };

        return callable;//new WebAsyncTask<Map>(10000, callable);
    }


}
