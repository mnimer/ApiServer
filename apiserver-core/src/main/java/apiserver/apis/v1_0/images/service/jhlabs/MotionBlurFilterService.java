package apiserver.apis.v1_0.images.service.jhlabs;

import apiserver.apis.v1_0.images.models.filters.MotionBlurModel;
import apiserver.apis.v1_0.images.wrappers.CachedImage;
import apiserver.exceptions.ColdFusionException;
import apiserver.exceptions.MessageConfigException;
import com.jhlabs.image.MotionBlurFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.integration.Message;

import java.awt.image.BufferedImage;

/**
 * User: mnimer
 * Date: 11/2/12
 */
@Slf4j
public class MotionBlurFilterService
{
    Logger log = Logger.getLogger(MotionBlurFilterService.class);

    public Object doFilter(Message<?> message) throws ColdFusionException, MessageConfigException
    {
        MotionBlurModel props = (MotionBlurModel) message.getPayload();

        float angle = props.getAngle();
        float distance = props.getDistance();
        float rotation = props.getRotation();
        boolean wrapEdges = props.isWrapEdges();
        float zoom = props.getZoom();
        CachedImage inFile = props.getCachedImage();

        try
        {

            if (inFile == null)
            {
                throw new MessageConfigException(MessageConfigException.MISSING_PROPERTY);
            }

            //run filter
            MotionBlurFilter filter = new MotionBlurFilter();
            filter.setAngle(angle);
            filter.setDistance(distance);
            filter.setRotation(rotation);
            filter.setWrapEdges(wrapEdges);
            filter.setZoom(zoom);
            BufferedImage bufferedImage = inFile.getBufferedImage();
            BufferedImage outFile = filter.filter(bufferedImage, null);

            props.setProcessedImage(outFile);
            return props;
        }
        catch (Throwable e)
        {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
