package apiserver.apis.v1_0.images.services.jhlabs;


import apiserver.apis.v1_0.images.gateways.jobs.filters.GlowJob;
import apiserver.exceptions.MessageConfigException;
import com.jhlabs.image.GlowFilter;
import org.apache.log4j.Logger;
import org.springframework.integration.Message;

import java.awt.image.BufferedImage;

/**
 * User: mnimer
 * Date: 10/31/12
 */
public class GlowFilterService
{
    Logger log = Logger.getLogger(GlowFilterService.class);

    public Object doFilter(Message<?> message) throws MessageConfigException
    {
        GlowJob props = (GlowJob) message.getPayload();

        int amount = props.getAmount();

        try
        {
            //run filter
            GlowFilter filter = new GlowFilter();
            filter.setAmount(amount);

            BufferedImage bufferedImage = props.getBufferedImage();
            if( bufferedImage == null )
            {
                throw new MessageConfigException(MessageConfigException.MISSING_PROPERTY);
            }

            BufferedImage outFile = filter.filter( bufferedImage, null );

            props.setBufferedImage(outFile);
            return message;
        }
        catch (Throwable e)
        {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
