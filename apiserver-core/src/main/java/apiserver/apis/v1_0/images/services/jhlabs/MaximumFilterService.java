package apiserver.apis.v1_0.images.services.jhlabs;

import apiserver.core.models.FileModel;
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
        FileModel props = (FileModel) message.getPayload();

        try
        {
            //run filter
            MaximumFilter filter = new MaximumFilter();

            BufferedImage bufferedImage = props.getBufferedImage();
            if( bufferedImage == null )
            {
                throw new MessageConfigException(MessageConfigException.MISSING_PROPERTY);
            }

            BufferedImage outFile = filter.filter( bufferedImage, null );

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