package apiserver.apis.v1_0.images.service.jhlabs;

import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.apis.v1_0.images.wrappers.CachedImage;
import apiserver.exceptions.ColdFusionException;
import apiserver.exceptions.MessageConfigException;
import com.jhlabs.image.ApplyMaskFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.Message;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * User: mnimer
 * Date: 10/31/12
 */
@Slf4j
public class ApplyMaskFilterService
{
    public Object doFilter(Message<?> message) throws ColdFusionException, MessageConfigException
    {
        Map props = (Map) message.getPayload();

        try
        {
            //InputStream in = new ByteArrayInputStream(FileHelper.fileBytes( props.get("file") ));
            //BufferedImage inFile = ImageIO.read(in);

            CachedImage inFile  = (CachedImage)props.get(ImageConfigMBeanImpl.FILE);
            CachedImage maskImage  = (CachedImage)props.get(ImageConfigMBeanImpl.MASK_FILE);

            BufferedImage inBufferedImage = inFile.getBufferedImage();
            BufferedImage destImage  = new BufferedImage(inBufferedImage.getWidth(), inBufferedImage.getHeight(), inBufferedImage.getType());

            if( inFile == null )
            {
                throw new MessageConfigException(MessageConfigException.MISSING_PROPERTY);
            }

            //run filter
            ApplyMaskFilter filter = new ApplyMaskFilter();
            filter.setDestination(destImage);
            filter.setMaskImage(maskImage.getBufferedImage());

            BufferedImage outFile = filter.filter( inBufferedImage, null );

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
