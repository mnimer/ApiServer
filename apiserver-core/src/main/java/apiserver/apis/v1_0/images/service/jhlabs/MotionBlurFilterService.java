package apiserver.apis.v1_0.images.service.jhlabs;

import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.apis.v1_0.images.wrappers.CachedImage;
import apiserver.exceptions.ColdFusionException;
import apiserver.exceptions.MessageConfigException;
import com.jhlabs.image.MinimumFilter;
import com.jhlabs.image.MotionBlurFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.integration.Message;

import java.awt.image.BufferedImage;
import java.util.Map;

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
        Map props = (Map) message.getPayload();
        float angle = ((Float)props.get("angle")).floatValue();
        float distance = ((Float)props.get("distance")).floatValue();
        float rotation = ((Float)props.get("rotation")).floatValue();
        boolean wrapEdges = ((Boolean)props.get("wrapEdges")).booleanValue();
        float zoom = ((Float)props.get("zoom")).floatValue();

        try
        {
            CachedImage inFile = (CachedImage) props.get(ImageConfigMBeanImpl.FILE);

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

            props.put(ImageConfigMBeanImpl.RESULT, outFile);
            return props;
        }
        catch (Throwable e)
        {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
