package apiserver.exceptions;

/**
 * Simple exception for controller methods that aren't coded yet.
 * Created by mnimer on 12/9/13.
 */
public class NotImplementedException extends Exception
{
    public NotImplementedException() {
        super();
    }

    public NotImplementedException(String message) {
        super(message);
    }

    public NotImplementedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotImplementedException(Throwable cause) {
        super(cause);
    }

    protected NotImplementedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
