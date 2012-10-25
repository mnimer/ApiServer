package apiserver.apis.v1_0.images.controllers;

import apiserver.apis.v1_0.common.HttpChannelInvoker;
import apiserver.apis.v1_0.images.ImageConfigMBean;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.exceptions.NotSupportedException;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 10/21/12
 */
@Controller
@RequestMapping("/image/metadata")
public class ImageMetadataController
{
    @Autowired
    private ImageConfigMBean imageConfigMBean;

    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    public HttpChannelInvoker channelInvoker;

    @Autowired
    public MessageChannel imageMetadataInputChannel;

    @Autowired
    public MessageChannel imageMetadataStripMetadataInputChannel;


    /**
     * get embedded metadata
     * @param cacheId - any valid URL or cache ID
     * @return  height,width, pixel size, transparency
     */
    @ApiOperation(value = "Get the embedded metadata", responseClass = "java.util.Map")
    @RequestMapping(value = "/{cacheId}/info", method =  {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView imageMetadataById(
            @ApiParam(name="cacheId", required = true) @PathVariable("cacheId") String cacheId)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.KEY, cacheId);

        return channelInvoker.invokeGenericChannel(request, null, args, imageMetadataInputChannel);
    }


    /**
     * get embedded metadata
     * @param file
     * @return   height,width, pixel size, transparency
     */
    @ApiOperation(value = "Get the embedded metadata", responseClass = "java.util.Map")
    @RequestMapping(value = "/info", method = {RequestMethod.POST, RequestMethod.PUT})
    public ModelAndView imageMetadataByImage(
            @ApiParam(name="file", required = true) @RequestParam("file") MultipartFile file )
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.FILE, file);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageMetadataInputChannel);

        view.getModel().remove(ImageConfigMBeanImpl.FILE);
        return view;
    }


    /**
     * remove the embedded metadata data for cached image. Striping out metadata may reduce file size (good for mobile apps) or remove sensitive data.
     * @param cacheId - any valid URL or cache ID
     * @return  height,width, pixel size, transparency
     */
    @ApiOperation(value = "Get the embedded metadata", responseClass = "java.util.Map")
    @RequestMapping(value = "/{cacheId}/clear", method =  {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView clearMetadataById(
            @ApiParam(name="cacheId", required = true) @PathVariable("cacheId") String cacheId)
            throws NotSupportedException
    {
        if( imageConfigMBean.getMetadataLibrary() != ImageConfigMBeanImpl.EXIFTOOL_METADATA_EXTRACTOR )
        {
            throw new NotSupportedException("Operation not support with current Metadata Library");
        }


        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.KEY, cacheId);

        return channelInvoker.invokeGenericChannel(request, null, args, imageMetadataStripMetadataInputChannel);
    }


    /**
     * remove the embedded metadata data for uploaded image. Striping out metadata may reduce file size (good for mobile apps) or remove sensitive data.
     * @param file
     * @return   height,width, pixel size, transparency
     */
    @ApiOperation(value = "Get the embedded metadata", responseClass = "java.util.Map")
    @RequestMapping(value = "/clear", method = {RequestMethod.POST, RequestMethod.PUT})
    public ModelAndView clearMetadataByImage(
            @ApiParam(name="file", required = true) @RequestParam("file") MultipartFile file )
            throws NotSupportedException
    {
        if( imageConfigMBean.getMetadataLibrary() != ImageConfigMBeanImpl.EXIFTOOL_METADATA_EXTRACTOR )
        {
            throw new NotSupportedException("Operation not support with current Metadata Library");
        }


        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.FILE, file);

        ModelAndView view = channelInvoker.invokeGenericChannel(request, null, args, imageMetadataStripMetadataInputChannel);

        view.getModel().remove(ImageConfigMBeanImpl.FILE);
        return view;
    }


}
