package apiserver.core.common.messageHandlers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.Lifecycle;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.channel.interceptor.ChannelInterceptorAdapter;
import org.springframework.integration.core.MessageSelector;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.util.Assert;

/**
 * User: mnimer
 * Date: 9/21/12
 */
public class PreOrPostWireTap extends ChannelInterceptorAdapter implements Lifecycle
{
    private static final Log logger = LogFactory.getLog(PreOrPostWireTap.class);

    private final MessageChannel preSendChannel;

    private final MessageChannel postSendChannel;

    private volatile long timeout = 0;

    private final MessageSelector selector;

    private volatile boolean running = true;


    /**
     * Create a new wire tap with <em>no</em> {@link MessageSelector}.
     *
     * @param preSendChannel  the MessageChannel to which intercepted messages will be sent
     * @param postSendChannel the MessageChannel to which intercepted messages will be sent
     */
    public PreOrPostWireTap(MessageChannel preSendChannel, MessageChannel postSendChannel)
    {
        this(preSendChannel, postSendChannel, null);
    }


    /**
     * Create a new wire tap with the provided {@link MessageSelector}.
     *
     * @param preSendChannel  the channel to which intercepted messages will be sent
     * @param postSendChannel the channel to which intercepted messages will be sent
     * @param selector        the selector that must accept a message for it to be
     *                        sent to the intercepting channel
     */
    public PreOrPostWireTap(MessageChannel preSendChannel, MessageChannel postSendChannel, MessageSelector selector)
    {
        Assert.isTrue((preSendChannel != null || postSendChannel != null), "at least one channel must be defined");
        this.preSendChannel = preSendChannel;
        this.postSendChannel = postSendChannel;
        this.selector = selector;
    }


    /**
     * Specify the timeout value for sending to the intercepting target.
     *
     * @param timeout the timeout in milliseconds
     */
    public void setTimeout(long timeout)
    {
        this.timeout = timeout;
    }


    /**
     * Check whether the wire tap is currently running.
     */
    @ManagedAttribute
    public boolean isRunning()
    {
        return this.running;
    }


    /**
     * Restart the wire tap if it has been stopped. It is running by default.
     */
    @ManagedOperation
    public void start()
    {
        this.running = true;
    }


    /**
     * Stop the wire tap. To restart, invoke {@link #start()}.
     */
    @ManagedOperation
    public void stop()
    {
        this.running = false;
    }


    /**
     * Intercept the Message and, <em>if accepted</em> by the {@link MessageSelector},
     * send it to the secondary target. If this wire tap's {@link MessageSelector} is
     * <code>null</code>, it will accept all messages.
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel)
    {
        if (this.preSendChannel.equals(channel) )
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("WireTap is refusing to intercept its own channel '" + preSendChannel + "'");
            }
            return message;
        }
        if (this.running && (this.selector == null || this.selector.accept(message)))
        {
            boolean preSent = this.preSendChannel.send(message);
            if (!preSent && logger.isWarnEnabled())
            {
                logger.warn("failed to send message to WireTap channel '" + this.preSendChannel + "'");
            }
        }
        return message;
    }


    /**
     *
     */
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent)
    {
        if (this.postSendChannel.equals(channel))
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("WireTap is refusing to intercept its own channel '" + this.postSendChannel + "'");
            }
        }
        if (this.running && (this.selector == null || this.selector.accept(message)))
        {
            boolean postSent = this.postSendChannel.send(message);
            if (!postSent && logger.isWarnEnabled())
            {
                logger.warn("failed to send message to WireTap channel '" + this.postSendChannel + "'");
            }
        }
    }
}
