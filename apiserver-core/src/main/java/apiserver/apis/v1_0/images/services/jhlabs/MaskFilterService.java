package apiserver.apis.v1_0.images.services.jhlabs;


import apiserver.apis.v1_0.images.models.filters.MaskModel;
import apiserver.exceptions.MessageConfigException;
import com.jhlabs.image.ApplyMaskFilter;
import org.apache.log4j.Logger;
import org.springframework.integration.Message;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * User: mnimer
 * Date: 10/31/12
 */
public class MaskFilterService
{
    Logger log = Logger.getLogger(MaskFilterService.class);

    public Object doFilter(Message<?> message) throws MessageConfigException
    {
        MaskModel props = (MaskModel) message.getPayload();

        Object maskImage = props.getMask();

        try
        {
            BufferedImage bufferedImage = props.getBufferedImage();
            if( bufferedImage == null )
            {
                throw new MessageConfigException(MessageConfigException.MISSING_PROPERTY);
            }

            BufferedImage destImage  = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage.getType());

            //run filter
            ApplyMaskFilter filter = new ApplyMaskFilter();
            filter.setDestination(destImage);

            filter.setMaskImage( bufferedImage );

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
