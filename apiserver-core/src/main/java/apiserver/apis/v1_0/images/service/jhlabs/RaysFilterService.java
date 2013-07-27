package apiserver.apis.v1_0.images.service.jhlabs;

import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.apis.v1_0.images.wrappers.CachedImage;
import apiserver.exceptions.ColdFusionException;
import apiserver.exceptions.MessageConfigException;
import com.jhlabs.image.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.integration.Message;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * User: mnimer
 * Date: 11/3/12
 */
public class RaysFilterService
{
    Logger log = Logger.getLogger(RaysFilterService.class);

    public Object doFilter(Message<?> message) throws ColdFusionException, MessageConfigException
    {
        Map props = (Map) message.getPayload();
        float opacity = ((Float)props.get("levels")).floatValue();
        float strength = ((Float)props.get("levels")).floatValue();
        float threshold = ((Float)props.get("levels")).floatValue();
        float angle = ((Float)props.get("levels")).floatValue();
        float centerX = ((Float)props.get("levels")).floatValue();
        float centerY = ((Float)props.get("levels")).floatValue();
        float distance = ((Float)props.get("levels")).floatValue();
        float rotation = ((Float)props.get("levels")).floatValue();
        float zoom = ((Float)props.get("levels")).floatValue();
        boolean raysOnly = ((Boolean)props.get("range")).booleanValue();

        String colorMapType = (String)props.get("colorMapType"); //gradient,grayscale,array,linear,spectrum,spline
        int[] gradientColors = (int[])props.get("gradientColors");
        int[] xKnots = (int[])props.get("xKnots");
        int[] yKnots = (int[])props.get("yKnots");
        int[] arrayColors = (int[])props.get("arrayColors");
        int linearColor1 = ((Integer)props.get("linearColor1")).intValue();
        int linearColor2 = ((Integer)props.get("linearColor2")).intValue();

        try
        {
            CachedImage inFile = (CachedImage) props.get(ImageConfigMBeanImpl.FILE);

            if (inFile == null)
            {
                throw new MessageConfigException(MessageConfigException.MISSING_PROPERTY);
            }

            Colormap colorMap = null;
            if( colorMapType.equalsIgnoreCase("gradient"))
            {
                colorMap = new Gradient(gradientColors);
            }
            else if( colorMapType.equalsIgnoreCase("grayscale"))
            {
                colorMap = new GrayscaleColormap();
            }
            else if( colorMapType.equalsIgnoreCase("array"))
            {
                colorMap = new ArrayColormap(arrayColors);
            }
            else if( colorMapType.equalsIgnoreCase("linear"))
            {
                colorMap = new LinearColormap(linearColor1, linearColor2);
            }
            else if( colorMapType.equalsIgnoreCase("spectrum"))
            {
                colorMap = new SpectrumColormap();
            }
            else if( colorMapType.equalsIgnoreCase("spline"))
            {
                colorMap = new SplineColormap(xKnots, yKnots);
            }



            //run filter
            RaysFilter filter = new RaysFilter();
            filter.setOpacity(opacity);
            filter.setRaysOnly(raysOnly);
            filter.setStrength(strength);
            filter.setThreshold(threshold);
            filter.setAngle(angle);
            filter.setCentreX(centerX);
            filter.setCentreY(centerY);
            filter.setDistance(distance);
            filter.setRotation(rotation);
            filter.setZoom(zoom);
            filter.setColormap(colorMap);
            BufferedImage bufferedImage = inFile.getBufferedImage();
            BufferedImage outFile = filter.filter(bufferedImage, null);

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
