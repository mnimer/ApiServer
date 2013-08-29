package apiserver.apis.v1_0.images.controllers;

import apiserver.apis.v1_0.images.gateways.images.ImageInfoGateway;
import apiserver.apis.v1_0.images.models.images.ImageInfoModel;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * User: mnimer
 * Date: 9/15/12
 */
@Controller
@RequestMapping("/image-info")
public class ImageInfoController
{
    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    private ImageInfoGateway gateway;

    /**
     * get basic info
     * @param cacheId - any valid URL or cache ID
     * @return  height,width, pixel size, transparency
     */
    @ApiOperation(value = "Get the height and width for the image", responseClass = "java.util.Map")
    @RequestMapping(value = "/{cacheId}/info", method =  {RequestMethod.GET})
    public Callable<Map> imageInfoById(
            @ApiParam(value = "cache id returned by /image-cache/add", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52") @PathVariable("cacheId") String cacheId)
    {
        final String _cacheId = cacheId;

        Callable<Map> callable = new Callable<Map>()
        {
            @Override
            public Map call() throws Exception
            {
                ImageInfoModel args = new ImageInfoModel();
                args.setCacheId(_cacheId);

                Future<Map> imageFuture = gateway.imageInfo(args);
                Map payload = imageFuture.get(10000, TimeUnit.MILLISECONDS);

                return payload;
            }
        };

        return callable;//new WebAsyncTask<Map>(10000, callable);
    }


    /**
     * get basic info
     * @param file
     * @return   height,width, pixel size, transparency
     * , @RequestPart("meta-data") Object metadata
     *
    , @RequestParam MultipartFile file

     */
    @ApiOperation(value = "Get the height and width for the image", responseClass = "java.util.Map")
    @RequestMapping(value = "/info", method = {RequestMethod.POST})
    public Callable<Map> imageInfoByImage(
            @ApiParam(value = "uploaded file to process", required = true) @RequestParam("file") MultipartFile file)
    {
        final MultipartFile _file = file;

        Callable<Map> callable = new Callable<Map>()
        {
            @Override
            public Map call() throws Exception
            {
                ImageInfoModel args = new ImageInfoModel();
                args.setFile(_file);

                Future<Map> imageFuture = gateway.imageInfo(args);
                Map payload = imageFuture.get(10000, TimeUnit.MILLISECONDS);

                return payload;
            }
        };

        return callable;//new WebAsyncTask<Map>(10000, callable);
    }



}
