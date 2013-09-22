package apiserver.core.common.interceptors;

import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.channel.ChannelInterceptor;

/**
 * User: mnimer
 * Date: 9/21/12
 */
public class TokenAccessInterceptor implements ChannelInterceptor
{
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel)
    {
        return message;
    }


    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent)
    {
        //
    }


    @Override
    public boolean preReceive(MessageChannel channel)
    {
        return true;
    }


    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel)
    {
        return message;
    }
}
