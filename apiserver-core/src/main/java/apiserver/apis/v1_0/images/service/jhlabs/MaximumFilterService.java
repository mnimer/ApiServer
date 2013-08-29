package apiserver.apis.v1_0.images.service.jhlabs;

import apiserver.apis.v1_0.images.models.ImageModel;
import apiserver.apis.v1_0.images.wrappers.CachedImage;
import apiserver.exceptions.MessageConfigException;
import com.jhlabs.image.MaximumFilter;
import org.apache.log4j.Logger;
import org.springframework.integration.Message;

import java.awt.image.BufferedImage;

/**
 * User: mnimer
 * Date: 10/31/12
 */
public class MaximumFilterService
{
    Logger log = Logger.getLogger(MaximumFilterService.class);

    public Object doFilter(Message<?> message) throws MessageConfigException
    {
        ImageModel props = (ImageModel) message.getPayload();

        try
        {
            CachedImage inFile  = props.getCachedImage();

            if( inFile == null )
            {
                throw new MessageConfigException(MessageConfigException.MISSING_PROPERTY);
            }

            //run filter
            MaximumFilter filter = new MaximumFilter();
            BufferedImage bufferedImage = inFile.getBufferedImage();
            BufferedImage outFile = filter.filter( bufferedImage, null );

            props.setProcessedFile(outFile);
            return message;
        }
        catch (Throwable e)
        {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
