package apiserver.apis.v1_0.common;

import org.springframework.integration.Message;

/**
 * User: mnimer
 * Date: 9/18/12
 */
public class NoOpService
{
    public Object noOpHandler(Message<?> message)
    {
        return null;
    }

}
