package apiserver.apis.v1_0.images.services.jhlabs;

import apiserver.apis.v1_0.images.gateways.jobs.filters.RaysJob;
import apiserver.exceptions.MessageConfigException;
import com.jhlabs.image.*;
import org.apache.log4j.Logger;
import org.springframework.integration.Message;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * User: mnimer
 * Date: 11/3/12
 */
public class RaysFilterService
{
    Logger log = Logger.getLogger(RaysFilterService.class);

    public Object doFilter(Message<?> message) throws MessageConfigException
    {
        RaysJob props = (RaysJob) message.getPayload();      //todo replace with real Model class
        float opacity = props.getOpacity();
        float strength = props.getStrength();
        float threshold = props.getThreshold();
        float angle = props.getAngle();
        float centerX = props.getCenterX();
        float centerY = props.getCenterY();
        float distance = props.getDistance();
        float rotation = props.getRotation();
        float zoom = props.getZoom();
        boolean raysOnly = props.getRaysOnly();

        String colorMapType = props.getColorMapType(); //gradient,grayscale,array,linear,spectrum,spline
        int[] gradientColors = props.getGradientColors();
        int[] xKnots = props.getxKnots();
        int[] yKnots = props.getyKnots();
        int[] arrayColors = props.getArrayColors();
        int linearColor1 = props.getLinearColor1();
        int linearColor2 = props.getLinearColor2();

        try
        {
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
            BufferedImage bufferedImage = props.getBufferedImage();
            BufferedImage outFile = filter.filter(bufferedImage, null);

            props.setBufferedImage(outFile);
            return props;
        }
        catch (Throwable e)
        {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
