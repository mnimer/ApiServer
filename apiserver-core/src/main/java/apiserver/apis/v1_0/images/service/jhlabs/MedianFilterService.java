package apiserver.apis.v1_0.images.service.jhlabs;

import apiserver.apis.v1_0.images.models.ImageModel;
import apiserver.apis.v1_0.images.wrappers.CachedImage;
import apiserver.exceptions.ColdFusionException;
import apiserver.exceptions.MessageConfigException;
import com.jhlabs.image.MedianFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.integration.Message;

import java.awt.image.BufferedImage;

/**
 * User: mnimer
 * Date: 10/31/12
 */
public class MedianFilterService
{
    Logger log = Logger.getLogger(MedianFilterService.class);

    public Object doFilter(Message<?> message) throws ColdFusionException, MessageConfigException
    {
        ImageModel props = (ImageModel) message.getPayload();
        CachedImage inFile  = (CachedImage)props.getCachedImage();

        try
        {

            if( inFile == null )
            {
                throw new MessageConfigException(MessageConfigException.MISSING_PROPERTY);
            }

            //run filter
            MedianFilter filter = new MedianFilter();
            BufferedImage bufferedImage = inFile.getBufferedImage();
            BufferedImage outFile = filter.filter( bufferedImage, null );

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
