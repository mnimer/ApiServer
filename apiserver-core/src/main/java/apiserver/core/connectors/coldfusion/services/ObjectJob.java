package apiserver.core.connectors.coldfusion.services;

/**
 * Job that invokes a CFC that will return data
 * Created by mnimer on 4/17/14.
 */
public interface ObjectJob
{
    Object getResult();
    void setResult(Object object);
}
