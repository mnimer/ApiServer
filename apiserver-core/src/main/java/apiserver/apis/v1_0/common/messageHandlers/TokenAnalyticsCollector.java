package apiserver.apis.v1_0.common.messageHandlers;

import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.channel.ChannelInterceptor;
import org.springframework.integration.handler.AbstractMessageHandler;

/**
 * User: mnimer
 * Date: 9/21/12
 */
public class TokenAnalyticsCollector extends AbstractMessageHandler
{
    @Override
    protected void handleMessageInternal(Message<?> message) throws Exception
    {
        //TODO: save information about this message to analytics store
    }
}
