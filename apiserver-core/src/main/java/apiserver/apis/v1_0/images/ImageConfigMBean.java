package apiserver.apis.v1_0.images;

import apiserver.exceptions.FactoryException;
import net.sf.ehcache.Cache;

/**
 * User: mnimer
 * Date: 10/19/12
 */
public interface ImageConfigMBean
{
    public Cache getCache() throws FactoryException;

    public String getCacheName();
    public void setCacheName(String cacheName);

    public String getCacheLibrary();
    public void setCacheLibrary(String cacheLibrary);

    public String getMetadataLibrary();
    public void setMetadataLibrary(String metadataLibrary);



    public String getImageInfoPath();
    public void setImageInfoPath(String path);

    public String getImageInfoMethod();
    public void setImageInfoMethod(String method);



    public String getImageBorderPath();
    public void setImageBorderPath(String path);

    public String getImageBorderMethod();
    public void setImageBorderMethod(String method);



    public String getImageTextPath();
    public void setImageTextPath(String path);

    public String getImageTextMethod();
    public void setImageTextMethod(String method);


    public String getImageResizePath();
    public void setImageResizePath(String path);

    public String getImageResizeMethod();
    public void setImageResizeMethod(String method);


    public String getImageRotatePath();
    public void setImageRotatePath(String path);

    public String getImageRotateMethod();
    public void setImageRotateMethod(String method);
}
