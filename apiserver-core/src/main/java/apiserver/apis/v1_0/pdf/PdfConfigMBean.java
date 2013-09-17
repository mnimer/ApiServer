package apiserver.apis.v1_0.pdf;

import apiserver.exceptions.FactoryException;
import net.sf.ehcache.Cache;

/**
 * User: mnimer
 * Date: 10/19/12
 */
public interface PdfConfigMBean
{
    public Cache getCache() throws FactoryException;

    public String getCacheName();
    public void setCacheName(String cacheName);

    public String getCacheLibrary();
    public void setCacheLibrary(String cacheLibrary);

    public String getConvertHtmlToPdfPath();
    public void setConvertHtmlToPdfPath(String path);

    public String getConvertHtmlToPdfMethod();
    public void setConvertHtmlToPdfMethod(String path);
}
