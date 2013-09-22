package apiserver.apis.v1_0.images.services.jhlabs;


import apiserver.apis.v1_0.images.models.filters.OilModel;
import apiserver.exceptions.MessageConfigException;
import com.jhlabs.image.OilFilter;
import org.apache.log4j.Logger;
import org.springframework.integration.Message;

import java.awt.image.BufferedImage;

/**
 * User: mnimer
 * Date: 11/3/12
 */
public class OilFilterService
{
    Logger log = Logger.getLogger(OilFilterService.class);

    public Object doFilter(Message<?> message) throws MessageConfigException
    {
        OilModel props = (OilModel) message.getPayload();
        int levels = props.getLevels();
        int range = props.getRange();

        try
        {

            //run filter
            OilFilter filter = new OilFilter();
            filter.setLevels(levels);
            filter.setRange(range);

            BufferedImage bufferedImage = props.getBufferedImage();
            if( bufferedImage == null )
            {
                throw new MessageConfigException(MessageConfigException.MISSING_PROPERTY);
            }

            BufferedImage outFile = filter.filter(bufferedImage, null);

            props.setProcessedFileBytes(outFile);
            return message;
        }
        catch (Exception e)
        {
            //log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }


}
