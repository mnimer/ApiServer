package apiserver.apis.v1_0.images.service.jhlabs;

import apiserver.ApiServerConstants;
import apiserver.apis.v1_0.images.FileHelper;
import apiserver.apis.v1_0.images.wrappers.CachedImage;
import apiserver.exceptions.ColdFusionException;
import apiserver.exceptions.MessageConfigException;
import com.jhlabs.image.BoxBlurFilter;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.Message;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User: mnimer
 * Date: 10/25/12
 */
@Slf4j
public class BoxBlurFilterService
{
    public Object doFilter(Message<?> message) throws ColdFusionException, MessageConfigException
    {
        Map props = (Map) message.getPayload();

        try
        {
            //InputStream in = new ByteArrayInputStream(FileHelper.fileBytes( props.get("file") ));
            //BufferedImage inFile = ImageIO.read(in);

            BufferedImage inFile  = (BufferedImage)props.get("file");

            if( inFile == null )
            {
                throw new MessageConfigException(MessageConfigException.MISSING_PROPERTY);
            }


            //BufferedImage outFile = new BufferedImage(inFile.getWidth(), inFile.getHeight(), inFile.getType());

            // Include filter service
            BoxBlurFilter filter = new BoxBlurFilter(
                    ((Integer) props.get("hRadius")).intValue()
                    , ((Integer) props.get("vRadius")).intValue()
                    , ((Integer) props.get("iterations")).intValue());

            //filter.setPremultiplyAlpha(((Boolean) props.get("premultiplyAlpha")).booleanValue());
            BufferedImage outFile = filter.filter( inFile, null );
            props.put("result", outFile);


            return props;
        }
        catch (Throwable e)
        {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
