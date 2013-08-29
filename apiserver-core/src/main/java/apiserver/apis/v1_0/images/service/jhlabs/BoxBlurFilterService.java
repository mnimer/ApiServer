package apiserver.apis.v1_0.images.service.jhlabs;

import apiserver.apis.v1_0.images.models.filters.BoxBlurModel;
import apiserver.apis.v1_0.images.wrappers.CachedImage;
import apiserver.exceptions.MessageConfigException;
import com.jhlabs.image.BoxBlurFilter;
import org.apache.log4j.Logger;
import org.springframework.integration.Message;

import java.awt.image.BufferedImage;

/**
 * User: mnimer
 * Date: 10/25/12
 */
public class BoxBlurFilterService
{
    Logger log = Logger.getLogger(BoxBlurFilterService.class);

    public Object doFilter(Message<?> message) throws MessageConfigException
    {
        BoxBlurModel props = (BoxBlurModel) message.getPayload();

        int hRadius = props.getHRadius();
        int vRadius = props.getVRadius();
        int iterations = props.getIterations();

        try
        {
            CachedImage inFile  = props.getCachedImage();

            if( inFile == null )
            {
                throw new MessageConfigException(MessageConfigException.MISSING_PROPERTY);
            }

            //run filter
            BoxBlurFilter filter = new BoxBlurFilter(hRadius, vRadius, iterations);

            //filter.setPremultiplyAlpha(((Boolean) props.get("premultiplyAlpha")).booleanValue());
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
