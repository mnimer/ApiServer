package apiserver.apis.v1_0.images.services.jhlabs;

import apiserver.apis.v1_0.images.models.filters.MotionBlurModel;
import apiserver.exceptions.MessageConfigException;
import com.jhlabs.image.MotionBlurFilter;
import org.apache.log4j.Logger;
import org.springframework.integration.Message;

import java.awt.image.BufferedImage;

/**
 * User: mnimer
 * Date: 11/2/12
 */
public class MotionBlurFilterService
{
    Logger log = Logger.getLogger(MotionBlurFilterService.class);

    public Object doFilter(Message<?> message) throws MessageConfigException
    {
        MotionBlurModel props = (MotionBlurModel) message.getPayload();

        float angle = props.getAngle();
        float distance = props.getDistance();
        float rotation = props.getRotation();
        boolean wrapEdges = props.isWrapEdges();
        float zoom = props.getZoom();

        try
        {

            //run filter
            MotionBlurFilter filter = new MotionBlurFilter();
            filter.setAngle(angle);
            filter.setDistance(distance);
            filter.setRotation(rotation);
            filter.setWrapEdges(wrapEdges);
            filter.setZoom(zoom);


            BufferedImage bufferedImage = props.getBufferedImage();
            if( bufferedImage == null )
            {
                throw new MessageConfigException(MessageConfigException.MISSING_PROPERTY);
            }

            BufferedImage outFile = filter.filter(bufferedImage, null);

            props.setProcessedFileBytes(outFile);
            return message;
        }
        catch (Throwable e)
        {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
