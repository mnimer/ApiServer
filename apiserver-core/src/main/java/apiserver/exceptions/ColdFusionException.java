package apiserver.exceptions;

/**
 * User: mnimer
 * Date: 9/16/12
 */
public class ColdFusionException extends Exception
{
    public ColdFusionException()
    {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }


    public ColdFusionException(String s)
    {
        super(s);    //To change body of overridden methods use File | Settings | File Templates.
    }


    public ColdFusionException(String s, Throwable throwable)
    {
        super(s, throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }


    public ColdFusionException(Throwable throwable)
    {
        super(throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
