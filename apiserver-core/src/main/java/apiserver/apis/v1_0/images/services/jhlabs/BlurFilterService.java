package apiserver.apis.v1_0.images.services.jhlabs;


import apiserver.apis.v1_0.images.gateways.jobs.ImageDocumentJob;
import apiserver.exceptions.MessageConfigException;
import com.jhlabs.image.BlurFilter;
import org.apache.log4j.Logger;
import org.springframework.integration.Message;

import java.awt.image.BufferedImage;

/**
 * User: mnimer
 * Date: 10/31/12
 */
public class BlurFilterService
{
    Logger log = Logger.getLogger(BlurFilterService.class);

    public Object doFilter(Message<?> message)
    {
        ImageDocumentJob props = (ImageDocumentJob) message.getPayload();
        //Map headers = (Map) message.getHeaders();

        try
        {
            //run filter
            BlurFilter filter = new BlurFilter();

            BufferedImage bufferedImage = props.getBufferedImage();
            if( bufferedImage == null )
            {
                throw new MessageConfigException(MessageConfigException.MISSING_PROPERTY);
            }

            BufferedImage outFile = filter.filter( bufferedImage, null );

            // add image into the payload, and return
            props.setBufferedImage(outFile);
            return message;
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
