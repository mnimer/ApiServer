package apiserver.apis.v1_0.images.services.jhlabs;

import apiserver.apis.v1_0.images.gateways.jobs.filters.GaussianJob;
import apiserver.exceptions.MessageConfigException;
import com.jhlabs.image.GaussianFilter;
import org.apache.log4j.Logger;
import org.springframework.integration.Message;

import java.awt.image.BufferedImage;

/**
 * User: mnimer
 * Date: 10/31/12
 */
public class GaussianFilterService
{
    Logger log = Logger.getLogger(GaussianFilterService.class);

    public Object doFilter(Message<?> message) throws MessageConfigException
    {
        GaussianJob props = (GaussianJob)message.getPayload();

        int radius = props.getRadius();

        try
        {
            //run filter
            GaussianFilter filter = new GaussianFilter(radius);

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
