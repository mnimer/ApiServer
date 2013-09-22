package apiserver.apis.v1_0.images.services.jhlabs;

import apiserver.apis.v1_0.images.models.filters.LensBlurModel;
import apiserver.exceptions.MessageConfigException;
import com.jhlabs.image.LensBlurFilter;
import org.apache.log4j.Logger;
import org.springframework.integration.Message;

import java.awt.image.BufferedImage;

/**
 * User: mnimer
 * Date: 10/31/12
 */
public class LensBlurFilterService
{
    Logger log = Logger.getLogger(LensBlurFilterService.class);

    public Object doFilter(Message<?> message) throws MessageConfigException
    {
        LensBlurModel props = (LensBlurModel) message.getPayload();

        float radius = props.getRadius();
        int sides = props.getSides();
        float bloom = props.getBloom();

        try
        {


            //run filter
            LensBlurFilter filter = new LensBlurFilter();
            filter.setBloom(bloom);
            filter.setSides(sides);
            filter.setRadius(radius);


            BufferedImage bufferedImage = props.getBufferedImage();
            if( bufferedImage == null )
            {
                throw new MessageConfigException(MessageConfigException.MISSING_PROPERTY);
            }

            BufferedImage outFile = filter.filter( bufferedImage, null );

            props.setProcessedFileBytes( outFile );
            return message;
        }
        catch (Throwable e)
        {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
