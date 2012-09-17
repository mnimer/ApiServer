package apiserver.exceptions;

/**
 * User: mnimer
 * Date: 9/16/12
 */
public class MessageConfigException extends Exception
{
    public static String MISSING_REQUEST_PROPERTY = "missingRequestProperty";
    public static String MISSING_RESPONSE_PROPERTY = "missingResponseProperty";


    public MessageConfigException()
    {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }


    public MessageConfigException(String s)
    {
        super(s);    //To change body of overridden methods use File | Settings | File Templates.
    }


    public MessageConfigException(String s, Throwable throwable)
    {
        super(s, throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }


    public MessageConfigException(Throwable throwable)
    {
        super(throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
