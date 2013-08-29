package apiserver.apis.v1_0.images.service.jhlabs;

import apiserver.apis.v1_0.images.models.filters.MaskModel;
import apiserver.apis.v1_0.images.wrappers.CachedImage;
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
            CachedImage inFile  = props.getCachedImage();

            BufferedImage inBufferedImage = inFile.getBufferedImage();
            BufferedImage destImage  = new BufferedImage(inBufferedImage.getWidth(), inBufferedImage.getHeight(), inBufferedImage.getType());

            if( inFile == null )
            {
                throw new MessageConfigException(MessageConfigException.MISSING_PROPERTY);
            }

            //run filter
            ApplyMaskFilter filter = new ApplyMaskFilter();
            filter.setDestination(destImage);

            if( maskImage instanceof CachedImage )
            {
                filter.setMaskImage( ((CachedImage)maskImage).getBufferedImage() );
            }
            else if( maskImage instanceof File)
            {
                BufferedImage bufferedImage = ImageIO.read((File)maskImage);
                filter.setMaskImage( bufferedImage );
            }
            else if( maskImage instanceof MultipartFile)
            {
                BufferedImage bufferedImage = ImageIO.read(  ((MultipartFile)maskImage).getInputStream()  );
                filter.setMaskImage( bufferedImage );
            }


            BufferedImage outFile = filter.filter( inBufferedImage, null );

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