package apiserver.apis.v1_0.images;

import apiserver.services.v1_0.CacheFactory;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/18/12
 */
public class ImageConfigMBean
{
    // Message params Arguments
    public static String FILE = "file";
    public static String KEY = "key";
    public static String TEXT = "text";
    public static String TIME_TO_LIVE = "timeToLive";
    public static String ANGLE = "angle";
    public static String INTERPOLATION = "interpolation";
    public static String WIDTH = "width";
    public static String HEIGHT = "height";
    public static String SCALE_TO_FIT = "scaleToFit";
    public static String COLOR = "color";
    public static String THICKNESS = "thickness";
    public static String FONT_SIZE = "fontSize";
    public static String FONT_STYLE = "fontStyle";
    public static String X = "x";
    public static String Y = "y";


    private CacheManager cacheManager = null;

    private String captchaEngine = "coldfusion";
    private String imageResizingEngine = "coldfusion";



    public CacheManager getCacheManager()
    {
        if( cacheManager == null )
        {
            cacheManager = CacheManager.newInstance();
        }
        return cacheManager;
    }


    public Cache getCacheByName( String name )
    {

        Cache _cache = CacheFactory.getCacheManager().getCache(name);
        if( _cache == null )
        {
            // create new cache
            cacheManager.addCache(name); //todo replace default cache config with cache configuration argument

            _cache = cacheManager.getCache(name);
        }

        return _cache;
    }



    public String getCaptchaEngine()
    {
        return captchaEngine;
    }


    public void setCaptchaEngine(String captchaEngine)
    {
        this.captchaEngine = captchaEngine;
    }


    public String getImageResizingEngine()
    {
        return imageResizingEngine;
    }


    public void setImageResizingEngine(String imageResizingEngine)
    {
        this.imageResizingEngine = imageResizingEngine;
    }


}
