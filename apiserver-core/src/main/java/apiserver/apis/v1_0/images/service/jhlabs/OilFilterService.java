package apiserver.apis.v1_0.images.service.jhlabs;

import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.apis.v1_0.images.wrappers.CachedImage;
import apiserver.exceptions.ColdFusionException;
import apiserver.exceptions.MessageConfigException;
import com.jhlabs.image.MotionBlurFilter;
import com.jhlabs.image.OilFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.Message;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * User: mnimer
 * Date: 11/3/12
 */
@Slf4j
public class OilFilterService
{

    public Object doFilter(Message<?> message) throws ColdFusionException, MessageConfigException
    {
        Map props = (Map) message.getPayload();
        int levels = ((Integer)props.get("levels")).intValue();
        int range = ((Integer)props.get("range")).intValue();

        try
        {
            CachedImage inFile = (CachedImage) props.get(ImageConfigMBeanImpl.FILE);

            if (inFile == null)
            {
                throw new MessageConfigException(MessageConfigException.MISSING_PROPERTY);
            }

            //run filter
            OilFilter filter = new OilFilter();
            filter.setLevels(levels);
            filter.setRange(range);
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
