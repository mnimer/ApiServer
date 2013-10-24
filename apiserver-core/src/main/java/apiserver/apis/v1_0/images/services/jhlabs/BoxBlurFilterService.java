package apiserver.apis.v1_0.images.services.jhlabs;


import apiserver.apis.v1_0.images.gateways.jobs.filters.BoxBlurJob;
import apiserver.exceptions.MessageConfigException;
import com.jhlabs.image.BoxBlurFilter;
import org.apache.log4j.Logger;
import org.springframework.integration.Message;

import java.awt.image.BufferedImage;

/**
 * User: mnimer
 * Date: 10/25/12
 */
public class BoxBlurFilterService
{
    Logger log = Logger.getLogger(BoxBlurFilterService.class);

    public Object doFilter(Message<?> message) throws MessageConfigException
    {
        BoxBlurJob props = (BoxBlurJob) message.getPayload();

        int hRadius = props.getHRadius();
        int vRadius = props.getVRadius();
        int iterations = props.getIterations();

        try
        {
            //run filter
            BoxBlurFilter filter = new BoxBlurFilter(hRadius, vRadius, iterations);

            //filter.setPremultiplyAlpha(((Boolean) props.get("premultiplyAlpha")).booleanValue());

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
