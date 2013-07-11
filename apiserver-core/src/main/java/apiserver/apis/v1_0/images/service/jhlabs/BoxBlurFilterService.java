package apiserver.apis.v1_0.images.service.jhlabs;

import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.apis.v1_0.images.wrappers.CachedImage;
import apiserver.exceptions.ColdFusionException;
import apiserver.exceptions.MessageConfigException;
import com.jhlabs.image.BoxBlurFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.integration.Message;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * User: mnimer
 * Date: 10/25/12
 */
@Slf4j
public class BoxBlurFilterService
{
    Logger log = Logger.getLogger(BoxBlurFilterService.class);

    public Object doFilter(Message<?> message) throws ColdFusionException, MessageConfigException
    {
        Map props = (Map) message.getPayload();

        try
        {
            //InputStream in = new ByteArrayInputStream(FileHelper.fileBytes( props.get("file") ));
            //BufferedImage inFile = ImageIO.read(in);

            CachedImage inFile  = (CachedImage)props.get(ImageConfigMBeanImpl.FILE);

            if( inFile == null )
            {
                throw new MessageConfigException(MessageConfigException.MISSING_PROPERTY);
            }

            //run filter
            BoxBlurFilter filter = new BoxBlurFilter(
                    ((Integer) props.get("hRadius")).intValue()
                    , ((Integer) props.get("vRadius")).intValue()
                    , ((Integer) props.get("iterations")).intValue());

            //filter.setPremultiplyAlpha(((Boolean) props.get("premultiplyAlpha")).booleanValue());
            BufferedImage bufferedImage = inFile.getBufferedImage();
            BufferedImage outFile = filter.filter( bufferedImage, null );


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
