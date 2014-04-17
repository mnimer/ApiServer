package apiserver.core.connectors.coldfusion.services;

import java.awt.image.BufferedImage;

/**
 * Created by mnimer on 4/17/14.
 */
public interface ImageJob
{
    BufferedImage getImage();
    void setImage(BufferedImage bufferedImage);
}
