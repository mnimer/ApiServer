package apiserver.core.connectors.coldfusion.services;

import java.util.Collection;

/**
 * Job that invokes a CFC that will return a Binary File
 * Created by mnimer on 4/17/14.
 */
public interface CollectionResult
{
    Collection getResult();
    void setResult(Collection bytes);
}
