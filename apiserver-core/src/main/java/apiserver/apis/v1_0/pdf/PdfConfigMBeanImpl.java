package apiserver.apis.v1_0.pdf;

import apiserver.exceptions.FactoryException;
import net.sf.ehcache.Cache;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * User: mnimer
 * Date: 9/18/12
 */
@Component
@ManagedResource(objectName = "com.apiserver:name=pdfApi")
public class PdfConfigMBeanImpl implements PdfConfigMBean
{
    public static String FILE = "file";
    public static String TIME_TO_LIVE = "timeToLive";
    public static String KEY = "__key";
    public static String RESULT = "__result";
    public static String CONTENT_TYPE = "__contentType";
    public static String ORIGINAL_FILE_NAME = "__originalFileName";
    public static String NAME = "__name";
    public static String SIZE = "__size";

    private String cacheName = "pdfApiCache";
    private Map<String, Cache> imageApiCache = new HashMap<String, Cache>();

    /**
     * ColdFusion CFC Paths
     */

    //convert HTML to PDF
    private String convertHTMLPath = "api-pdfhtml.cfc?method=htmlToPdf";
    private String convertHTMLMethod = "GET";

    public PdfConfigMBeanImpl()
    {
        initializeSampleResources();

    }


    private void initializeSampleResources()
    {
        try
        {
            //TODO see ImageConfigMBean
        }
        catch (Exception ex1)
        {
            //do nothing
        }
    }

    @ManagedAttribute(description = "get convert html to PDF cfc path")
    public String getConvertHtmlToPdfPath()
    {
        return convertHTMLPath;
    }


    @ManagedAttribute(description = "get convert html to PDF cfc path", persistPolicy = "OnUpdate")
    public void setConvertHtmlToPdfPath(String convertHTMLPath)
    {
        this.convertHTMLPath = convertHTMLPath;
    }


    @ManagedAttribute(description = "get convert html to PDF HTTP method")
    public String getConvertHtmlToPdfMethod()
    {
        return convertHTMLMethod;
    }


    @ManagedAttribute(description = "get convert html to PDF cfc method", persistPolicy = "OnUpdate")
    public void setConvertHtmlToPdfMethod(String convertHTMLMethod)
    {
        this.convertHTMLMethod = convertHTMLMethod;
    }
}
