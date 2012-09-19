package apiserver.apis.v1_0.images.controllers;

import apiserver.apis.v1_0.common.HttpChannelInvoker;
import apiserver.apis.v1_0.images.ImageConfigMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     * @param request
     * @param response
     * @param file
     * @param timeToLiveInSeconds
     * @return cache ID
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addImage(
            HttpServletRequest request
            , HttpServletResponse response
            , @RequestParam(required = true) MultipartFile file
            , @RequestParam(required = true, defaultValue = "0") Integer timeToLiveInSeconds )
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.FILE, file);
        args.put(ImageConfigMBean.TIME_TO_LIVE, timeToLiveInSeconds);

        return channelInvoker.invokeGenericChannel(request, response, args, imageCacheAddInputChannel);
    }



    /**
     * pull image out of cache
     * @param request
     * @param response
     * @param cacheId
     * @return
     */
    @RequestMapping(value = "/{cacheId}/get", method = RequestMethod.GET)
    public ModelAndView getImage(
            HttpServletRequest request
            , HttpServletResponse response
            , @PathVariable(value = "cacheId") String cacheId )
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.KEY, cacheId);

        return channelInvoker.invokeGenericChannel(request, response, args, imageCacheGetInputChannel);
    }



    /**
     * pull image out of cache
     * @param request
     * @param response
     * @param cacheId
     * @return
     */
    @RequestMapping(value = "/{cacheId}/delete", method = RequestMethod.GET)
    public ModelAndView deleteImage(
            HttpServletRequest request
            , HttpServletResponse response
            , @PathVariable(value = "cacheId") String cacheId )
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBean.KEY, cacheId);

        return channelInvoker.invokeGenericChannel(request, response, args, imageCacheDeleteInputChannel);
    }


}
