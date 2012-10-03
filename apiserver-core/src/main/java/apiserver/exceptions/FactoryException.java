package apiserver.exceptions;

/**
 * User: mnimer
 * Date: 9/27/12
 */
public class FactoryException extends Exception
{

    public FactoryException(String s)
    {
        super(s);    //To change body of overridden methods use File | Settings | File Templates.
    }


    public FactoryException(String s, Throwable throwable)
    {
        super(s, throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }


    public FactoryException(Throwable throwable)
    {
        super(throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
