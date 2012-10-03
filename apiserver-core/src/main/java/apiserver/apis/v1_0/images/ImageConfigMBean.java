package apiserver.apis.v1_0.images;

import apiserver.exceptions.FactoryException;
import apiserver.services.v1_0.cache.CacheFactory;
import apiserver.services.v1_0.cache.CacheServiceMBean;
import apiserver.services.v1_0.cache.ICacheService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

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


    private String cacheName = "imageApiCache";
    private String cacheLibrary = CacheServiceMBean.EHCACHE;
    private Map<String, Cache> imageApiCache = new HashMap<String, Cache>();



    public Cache getCache() throws FactoryException
    {
        if( imageApiCache.get(getCacheName()) == null )
        {
            Cache _cache = new Cache(new CacheConfiguration(getCacheName(), 1000).eternal(true).memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.CLOCK));
            _cache.initialise();

            imageApiCache.put(getCacheName(), _cache);
        }
        return imageApiCache.get(getCacheName());
        /**

        Object _cacheManager = CacheFactory.getCacheManager(getCacheName());

        if( _cacheManager instanceof net.sf.ehcache.CacheManager )
        {
            Cache _cache = ((CacheManager)_cacheManager).getCache(getCacheName());
            if( _cache == null )
            {
                // create new cache
                //_cache = ((CacheManager)_cacheManager).getCache(getCacheName());

            }

            return _cache;
        }

        return null;
         **/
    }


    public String getCacheName()
    {
        return cacheName;
    }


    public void setCacheName(String cacheName)
    {
        this.cacheName = cacheName;
    }


    public String getCacheLibrary()
    {
        return cacheLibrary;
    }


    public void setCacheLibrary(String cacheLibrary)
    {
        this.cacheLibrary = cacheLibrary;
    }

}
