package apiserver.apis.v1_0.images.service.jhlabs;

import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.apis.v1_0.images.wrappers.CachedImage;
import apiserver.exceptions.ColdFusionException;
import apiserver.exceptions.MessageConfigException;
import com.jhlabs.image.BlurFilter;
import com.jhlabs.image.BumpFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.Message;

import java.awt.image.BufferedImage;
import java.awt.image.Kernel;
import java.util.Map;

/**
 * This filter does a simple convolution which emphasises edges in an image.
 *
 * User: mnimer
 * Date: 10/31/12
 */
@Slf4j
public class BumpFilterService
{
    public Object doFilter(Message<?> message) throws ColdFusionException, MessageConfigException
    {
        Map props = (Map) message.getPayload();

        try
        {
            int edgeAction = ((Integer)props.get("edgeAction")).intValue();
            boolean useAlpha = ((Boolean)props.get("useAlpha")).booleanValue();
            float[] embossMatrix = ((float[])props.get("matrix"));

            // calculate
            int rows = new Double(Math.sqrt( new Integer(embossMatrix.length).doubleValue() )).intValue();
            int cols = new Double(Math.sqrt( new Integer(embossMatrix.length).doubleValue() )).intValue();

            //InputStream in = new ByteArrayInputStream(FileHelper.fileBytes( props.get("file") ));
            //BufferedImage inFile = ImageIO.read(in);

            CachedImage inFile  = (CachedImage)props.get(ImageConfigMBeanImpl.FILE);

            if( inFile == null )
            {
                throw new MessageConfigException(MessageConfigException.MISSING_PROPERTY);
            }

            //run filter
            BumpFilter filter = new BumpFilter();
            filter.setEdgeAction(edgeAction);
            filter.setUseAlpha(useAlpha);
            filter.setKernel( new Kernel(rows, cols, embossMatrix));
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
