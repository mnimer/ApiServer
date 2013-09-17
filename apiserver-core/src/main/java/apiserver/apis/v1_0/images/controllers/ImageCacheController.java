package apiserver.apis.v1_0.images.controllers;

import apiserver.apis.v1_0.common.ResponseEntityHelper;
import apiserver.apis.v1_0.images.gateways.cache.CacheAddGateway;
import apiserver.apis.v1_0.images.gateways.cache.CacheDeleteGateway;
import apiserver.apis.v1_0.images.gateways.cache.CacheGetGateway;
import apiserver.apis.v1_0.images.models.cache.CacheAddModel;
import apiserver.apis.v1_0.images.models.cache.CacheDeleteModel;
import apiserver.apis.v1_0.images.models.cache.CacheGetModel;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;

/**
 * User: mnimer
 * Date: 9/18/12
 */
//Controller
@RequestMapping("/image-cache")
public class ImageCacheController
{
    @Autowired
    public CacheAddGateway cacheAddGateway;
    @Autowired
    public CacheGetGateway cacheGetGateway;
    @Autowired
    public CacheDeleteGateway cacheDeleteGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;


    /**
     * put image into cache, usable for future manipulations of the image
     *
     * @param file
     * @param timeToLiveInSeconds
     * @return cache ID
     */
    @ApiOperation(value = "add an image to the cache")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.PUT})

    //@GET
    //@Path("/{petId}")
    //@ApiOperation(value = "Find pet by ID", notes = "Add extra notes here", responseClass = "com.wordnik.swagger.sample.model.Pet")
    public Callable<Map> addImage(
            @ApiParam(name = "file", required = true) @RequestParam(required = true) MultipartFile file
            , @ApiParam(name = "timeToLiveInSeconds", required = true) @RequestParam(required = true, defaultValue = "0") Integer timeToLiveInSeconds)
            throws InterruptedException, TimeoutException, ExecutionException
    {
        final MultipartFile _file = file;
        final Integer _timeToLiveInSeconds = timeToLiveInSeconds;

        Callable<Map> callable = new Callable<Map>()
        {
            @Override
            public Map call() throws Exception
            {
                CacheAddModel args = new CacheAddModel();
                args.setMultipartFile(_file);
                args.setTimeToLiveInSeconds(_timeToLiveInSeconds);

                Future<Map> imageFuture = cacheAddGateway.addToCache(args);
                Map payload = imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

                return payload;
            }
        };

        return callable;//new WebAsyncTask<Map>(10000, callable);
    }


    /**
     * pull image out of cache
     *
     * @param cacheId
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "get an image from cache")
    @RequestMapping(value = "/{cacheId}/get", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> getImage(
            @ApiParam(name = "cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52") @PathVariable(value = "cacheId") String cacheId
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws InterruptedException, TimeoutException, ExecutionException, IOException
    {
        CacheGetModel args = new CacheGetModel();
        args.setCacheId(cacheId);

        Future<Map> imageFuture = cacheGetGateway.getFromCache(args);
        CacheGetModel payload = (CacheGetModel) imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = payload.getProcessedFile();
        String contentType = payload.getContentType();
        ResponseEntity<byte[]> result = ResponseEntityHelper.processImage(bufferedImage, contentType, returnAsBase64);
        return result;

    }


    /**
     * pull image out of cache
     *
     * @param cacheId
     * @return
     */
    @ApiOperation(value = "delete an image in cache")
    @RequestMapping(value = "/{cacheId}/delete", method = {RequestMethod.GET, RequestMethod.DELETE})
    public Callable<Boolean> deleteImage(
            @ApiParam(name = "cacheId", required = true) @PathVariable(value = "cacheId") String cacheId)
    {
        final String _cacheId = cacheId;

        Callable<Boolean> callable = new Callable<Boolean>()
        {
            @Override
            public Boolean call() throws Exception
            {
                CacheDeleteModel args = new CacheDeleteModel();
                args.setCacheId(_cacheId);

                try
                {
                    Future<Boolean> imageFuture = cacheDeleteGateway.deleteFromCache(args);
                    Object payload = imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

                    return Boolean.TRUE;
                }catch (Exception ex){
                    return Boolean.FALSE;
                }
            }
        };

        return callable;//new WebAsyncTask<Map>(10000, callable);
    }


}
