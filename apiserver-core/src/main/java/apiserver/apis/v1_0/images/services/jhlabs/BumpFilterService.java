package apiserver.apis.v1_0.images.services.jhlabs;


import apiserver.apis.v1_0.images.models.filters.BumpModel;
import apiserver.exceptions.MessageConfigException;
import com.jhlabs.image.BumpFilter;
import org.apache.log4j.Logger;
import org.springframework.integration.Message;

import java.awt.image.BufferedImage;
import java.awt.image.Kernel;

/**
 * This filter does a simple convolution which emphasises edges in an image.
 *
 * User: mnimer
 * Date: 10/31/12
 */
public class BumpFilterService
{
    Logger log = Logger.getLogger(BumpFilterService.class);

    public Object doFilter(Message<?> message) throws MessageConfigException
    {
        BumpModel props = (BumpModel) message.getPayload();

        int edgeAction = props.getEdgeAction();
        boolean useAlpha = props.isUseAlpha();
        float[] embossMatrix = props.getMatrix();

        try
        {

            // calculate
            int rows = new Double(Math.sqrt( new Integer(embossMatrix.length).doubleValue() )).intValue();
            int cols = new Double(Math.sqrt( new Integer(embossMatrix.length).doubleValue() )).intValue();

            //run filter
            BumpFilter filter = new BumpFilter();
            filter.setEdgeAction(edgeAction);
            filter.setUseAlpha(useAlpha);
            filter.setKernel( new Kernel(rows, cols, embossMatrix));

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
