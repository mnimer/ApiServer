package apiserver.core.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.channel.ChannelInterceptor;

/**
 * User: mikenimer
 * Date: 7/18/13
 */
public class DebugTraceInterceptor implements ChannelInterceptor
{
    public final Logger log = LoggerFactory.getLogger(DebugTraceInterceptor.class);


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel)
    {
        //log.debug("[PRE SEND] channel:" +channel +"      message:" +message.getPayload());
        return message;
    }


    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent)
    {
        //log.debug("[POST SEND] channel:" +channel );
    }


    @Override
    public boolean preReceive(MessageChannel channel)
    {
        return true;
    }


    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel)
    {
        //log.debug("[POST RECEIVE] channel:" +channel );
        return message;
    }
}
