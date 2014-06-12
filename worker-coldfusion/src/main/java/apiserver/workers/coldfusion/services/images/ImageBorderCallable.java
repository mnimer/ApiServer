package apiserver.workers.coldfusion.services.images;

import apiserver.workers.coldfusion.GridManager;
import apiserver.workers.coldfusion.exceptions.ColdFusionException;
import coldfusion.cfc.CFCProxy;
import coldfusion.image.Image;
import org.gridgain.grid.lang.GridCallable;

/**
 * Created by mnimer on 6/10/14.
 */
public class ImageBorderCallable implements GridCallable
{
    private byte[] image;
    private String color;
    private Integer thickness;
    private String format;


    public ImageBorderCallable(byte[] image, String format, String color, Integer thickness) {
        this.image = image;
        this.format = format;
        this.color = color;
        this.thickness = thickness;
    }


    @Override
    public byte[] call() throws Exception {
        String cfcPath = GridManager.rootPath + "/apiserver-inf/components/v1/api-image.cfc";
        try {
            System.out.println("Invoking Grid Service: api-image.cfc::addBorder ");

            // Invoke CFC
            CFCProxy proxy = new CFCProxy(cfcPath, false);
            Image result = (Image) proxy.invoke("addBorder", new Object[]{this.image, this.color, this.thickness});

            // Convert BuffereredImage back to regular image so we can return the bytes
            return result.getImageBytes(this.format);
        }
        catch (Throwable e) {
            //e.printStackTrace();
            throw new ColdFusionException("Error Invoking FileBorderService on grid", e);
        }
    }
}
