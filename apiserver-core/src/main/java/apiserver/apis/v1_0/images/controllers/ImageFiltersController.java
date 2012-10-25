package apiserver.apis.v1_0.images.controllers;

import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.core.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Date: 9/27/12
 */

@Controller
@RequestMapping("/image/filters")
public class ImageFiltersController
{
    @Autowired(required = false)
    private HttpServletRequest request;


    @ApiOperation(value="")
    @RequestMapping(value = "/{cacheId}/grayscale", method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView imageInfoById(
            @ApiParam(name="cacheId", required = true) @PathVariable("cacheId") String cacheId)
    {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(ImageConfigMBeanImpl.KEY, cacheId);

        throw new RuntimeException("not implemented yet");

    }

    @ApiOperation(value="")
    @RequestMapping(value = "/grayscale", method = {RequestMethod.POST, RequestMethod.PUT})
    public ModelAndView imageInfoById(
            @ApiParam(name="file", required = true) @RequestParam MultipartFile file)
    {
        Map<String, Object> args = new HashMap<String, Object>();

        throw new RuntimeException("not implemented yet");
    }
}
