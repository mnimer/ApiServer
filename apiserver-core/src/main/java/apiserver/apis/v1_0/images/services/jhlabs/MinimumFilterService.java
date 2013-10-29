package apiserver.apis.v1_0.images.services.jhlabs;

import apiserver.apis.v1_0.images.gateways.jobs.ImageDocumentJob;
import apiserver.exceptions.MessageConfigException;
import com.jhlabs.image.MinimumFilter;
import org.apache.log4j.Logger;
import org.springframework.integration.Message;

import java.awt.image.BufferedImage;

/**
 * User: mnimer
 * Date: 11/1/12
 */
public class MinimumFilterService
{
    Logger log = Logger.getLogger(MinimumFilterService.class);

    public Object doFilter(Message<?> message) throws MessageConfigException
    {
        ImageDocumentJob props = (ImageDocumentJob) message.getPayload();

        try
        {
            //run filter
            MinimumFilter filter = new MinimumFilter();

            BufferedImage bufferedImage = props.getBufferedImage();
            if( bufferedImage == null )
            {
                throw new MessageConfigException(MessageConfigException.MISSING_PROPERTY);
            }

            BufferedImage outFile = filter.filter(bufferedImage, null);

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
