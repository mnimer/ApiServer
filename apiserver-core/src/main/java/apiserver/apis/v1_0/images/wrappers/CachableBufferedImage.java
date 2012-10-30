package apiserver.apis.v1_0.images.wrappers;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.util.Hashtable;

/**
 * User: mnimer
 * Date: 10/29/12
 */
public class CachableBufferedImage extends BufferedImage
{
    private String contentType;

    public CachableBufferedImage(int i, int i1, int i2)
    {
        super(i, i1, i2);
    }


    public CachableBufferedImage(int i, int i1, int i2, IndexColorModel indexColorModel)
    {
        super(i, i1, i2, indexColorModel);
    }


    public CachableBufferedImage(ColorModel colorModel, WritableRaster writableRaster, boolean b, Hashtable<?, ?> hashtable)
    {
        super(colorModel, writableRaster, b, hashtable);
    }


    public String getContentType()
    {
        return contentType;
    }


    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }
}
