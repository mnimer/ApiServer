package apiserver.workers.coldfusion.services.images;

import apiserver.workers.coldfusion.GridManager;
import apiserver.workers.coldfusion.exceptions.ColdFusionException;
import coldfusion.cfc.CFCProxy;
import coldfusion.image.Image;
import org.gridgain.grid.lang.GridCallable;

/**
 * Created by mnimer on 6/10/14.
 */
public class ImageResizeCallable implements GridCallable
{
    private byte[] image;
    private String format;
    private Integer width;
    private Integer height;
    private String interpolation = "bicubic";
    private Boolean scaleToFit = false;


    public ImageResizeCallable(byte[] image, String format, Integer width, Integer height, String interpolation, Boolean scaleToFit) {
        this.image = image;
        this.format = format;
        this.width = width;
        this.height = height;
        this.interpolation = interpolation;
        this.scaleToFit = scaleToFit;
    }


    @Override
    public byte[] call() throws Exception {

        String cfcPath = GridManager.rootPath + "/apiserver-inf/components/v1/api-image.cfc";
        try {
            System.out.println("Invoking Grid Service: api-image.cfc::resizeImage ");

            CFCProxy proxy = new CFCProxy(cfcPath, false);
            Image result = (Image) proxy.invoke("resizeImage", new Object[]{this.image, this.width, this.height, this.interpolation, this.scaleToFit});
            return result.getImageBytes(this.format);
        }
        catch (Throwable e) {
            e.printStackTrace();
            throw new ColdFusionException("Error Invoking FileBorderService", e);
        }
    }
}
