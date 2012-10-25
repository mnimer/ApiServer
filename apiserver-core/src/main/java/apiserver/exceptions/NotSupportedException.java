package apiserver.exceptions;

/**
 * User: mnimer
 * Date: 10/21/12
 */
public class NotSupportedException extends Exception
{
    public NotSupportedException(String s)
    {
        super(s);
    }


    public NotSupportedException(String s, Throwable throwable)
    {
        super(s, throwable);
    }


    public NotSupportedException(Throwable throwable)
    {
        super(throwable);
    }
}
