package apiserver.apis.v1_0.images;

import apiserver.apis.v1_0.common.HttpChannelInvoker;
import apiserver.apis.v1_0.images.service.cache.ImageCacheService;
import apiserver.apis.v1_0.images.wrappers.CachedImage;
import apiserver.exceptions.FactoryException;
import apiserver.services.v1_0.cache.CacheServiceMBean;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.MessageChannel;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


/**
 * User: mnimer
 * Date: 9/18/12
 */
@Component
@ManagedResource(objectName = "com.apiserver:name=ImageApi")
public class ImageConfigMBeanImpl implements ImageConfigMBean
{
    // Message params Arguments
    public static String FILE = "__file";
    public static String MASK_FILE = "__maskfile";
    public static String CONTENT_TYPE = "__contentType";
    public static String ORIGINAL_FILE_NAME = "__originalFileName";
    public static String NAME = "__name";
    public static String SIZE = "__size";
    public static String KEY = "__key";
    public static String MASK_CACHEID = "__maskKey";
    public static String RESULT = "__result";
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
    public static String DREW_METADATA_EXTRACTOR = "drewMetadataExtractor";
    public static String EXIFTOOL_METADATA_EXTRACTOR = "exifTool";


    private String cacheName = "imageApiCache";
    private String cacheLibrary = CacheServiceMBean.EHCACHE;
    private String metadataLibrary = ImageConfigMBeanImpl.EXIFTOOL_METADATA_EXTRACTOR;
    private Map<String, Cache> imageApiCache = new HashMap<String, Cache>();


    public ImageConfigMBeanImpl()
    {
        initializeSampleResources();

    }


    private void initializeSampleResources()
    {
        try
        {
            String key = "a3c8af38-82e3-4241-8162-28e17ebcbf52";
            File file = new File(getClass().getClassLoader().getResource("sample.png").toURI());

            CachedImage cachedImage = new CachedImage(file);
            cachedImage.setFileName(file.getName());
            cachedImage.setSize( file.length() );
            cachedImage.setContentType( "image/png" );

            Map cachedProperties = new HashMap();
            cachedProperties.put(ImageConfigMBeanImpl.FILE, cachedImage);
            cachedProperties.put(ImageConfigMBeanImpl.CONTENT_TYPE, "image/png" );
            cachedProperties.put(ImageConfigMBeanImpl.ORIGINAL_FILE_NAME, file.getName() );
            cachedProperties.put(ImageConfigMBeanImpl.NAME, file.getName() );
            cachedProperties.put(ImageConfigMBeanImpl.SIZE, file.length() );


            Element element = new Element(key, cachedProperties );
            element.setEternal(true);

            getCache().put(element);

            /**
            Map<String, Object> args = new HashMap<String, Object>();
            args.put(ImageConfigMBeanImpl.FILE, file);
            args.put(ImageConfigMBeanImpl.TIME_TO_LIVE, 0);
            ModelAndView view = channelInvoker.invokeGenericChannel(null, null, args, imageCacheAddInputChannel);
            **/
        }
        catch (Exception ex1)
        {
            //do nothing
        }
    }


    public Cache getCache() throws FactoryException
    {
        if (imageApiCache.get(getCacheName()) == null)
        {
            Cache _cache = new Cache(new CacheConfiguration(getCacheName(), 1000).eternal(true).memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.CLOCK));
            _cache.initialise();

            imageApiCache.put(getCacheName(), _cache);
        }
        return imageApiCache.get(getCacheName());
    }


    @ManagedAttribute(description = "get cache name for the system image collection")
    public String getCacheName()
    {
        return cacheName;
    }


    @ManagedAttribute(description = "set cache name for the system image collection, default is: 'imageApiCache'")
    public void setCacheName(String cacheName)
    {
        this.cacheName = cacheName;
    }


    @ManagedAttribute(description = "get cache library for image operations")
    public String getCacheLibrary()
    {
        return cacheLibrary;
    }


    @ManagedAttribute(description = "set cache library for image operations, possible values are: 'ehcache' (default), 'jcache'")
    public void setCacheLibrary(String cacheLibrary)
    {
        this.cacheLibrary = cacheLibrary;
    }


    @ManagedAttribute(description = "get metadata library")
    public String getMetadataLibrary()
    {
        return metadataLibrary;
    }


    @ManagedAttribute(description = "set metadata library, possible values are: 'drewMetadataExtractor', 'exifTool' (default)", persistPolicy = "OnUpdate")
    public void setMetadataLibrary(String metadataLibrary)
    {
        this.metadataLibrary = metadataLibrary;
    }
}
