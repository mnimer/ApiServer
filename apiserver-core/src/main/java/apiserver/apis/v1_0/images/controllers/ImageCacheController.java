package apiserver.apis.v1_0.images.controllers;

import apiserver.apis.v1_0.common.HttpChannelInvoker;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.apis.v1_0.images.wrappers.CachedImage;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.core.ApiOperation;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/18/12
 */

@Controller
@RequestMapping("/image/cache")
public class ImageCacheController
{
    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    public HttpChannelInvoker channelInvoker;

    @Autowired
    public MessageChannel imageCacheAddInputChannel;
    @Autowired
    public MessageChannel imageCacheGetInputChannel;
    @Autowired
    public MessageChannel imageCacheDeleteInputChannel;


    /**
     * put image into cache, usable for future manipulations of the image
     * @param file
     * @param timeToLiveInSeconds
     * @return cache ID
     */
    @ApiOperation(value = "add an image to the cache")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.PUT})
    public ModelAndView addImage(
            @ApiParam(name="file", required = true) @RequestParam(required = true) MultipartFile file
            ,@ApiParam(name="timeToLiveInSeconds", required = true)  @RequestParam(required = true, defaultValue = "0") Integer timeToLiveInSeconds )
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.FILE, file);
        args.put(ImageConfigMBeanImpl.TIME_TO_LIVE, timeToLiveInSeconds);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageCacheAddInputChannel);
        view.getModel().remove(ImageConfigMBeanImpl.FILE);
        view.getModel().remove(ImageConfigMBeanImpl.TIME_TO_LIVE);
        return view;
    }



    /**
     * pull image out of cache
     * @param cacheId
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "get an image from cache")
    @RequestMapping(value = "/{cacheId}/get", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> getImage(
            @ApiParam(name="cacheId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52") @PathVariable(value = "cacheId") String cacheId
            , @ApiParam(name = "returnAsBase64", required = false, defaultValue = "true", allowableValues = "true,false") @RequestParam(value = "returnAsBase64", required = false, defaultValue = "false") Boolean returnAsBase64
    ) throws IOException
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.KEY, cacheId);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageCacheGetInputChannel);

        CachedImage cachedImage = (CachedImage)view.getModel().get(ImageConfigMBeanImpl.RESULT);
        ResponseEntity<byte[]> result = channelInvoker.imageResultHandler(  cachedImage.getFileBytes(), cachedImage.getContentType(), returnAsBase64);
        return result;
    }



    /**
     * pull image out of cache
     * @param cacheId
     * @return
     */
    @ApiOperation(value = "delete an image in cache")
    @RequestMapping(value = "/{cacheId}/delete", method = {RequestMethod.GET,RequestMethod.DELETE})
    public ModelAndView deleteImage(
            @ApiParam(name="cacheId", required = true) @PathVariable(value = "cacheId") String cacheId )
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.KEY, cacheId);

        return channelInvoker.invokeGenericChannel(request, null, args, imageCacheDeleteInputChannel);
    }


}
