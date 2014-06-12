package apiserver.workers.coldfusion.services.images;

import apiserver.workers.coldfusion.GridManager;
import apiserver.workers.coldfusion.exceptions.ColdFusionException;
import coldfusion.cfc.CFCProxy;
import coldfusion.image.Image;
import org.gridgain.grid.lang.GridCallable;

/**
 *
 *
 <cffunction name="rotateImage" output="no" access="remote" returnformat="plain">
     <cfargument name="image"/>
     <cfargument name="angle" default="90"/>
 *
 * Created by mnimer on 6/10/14.
 */
public class ImageRotateCallable implements GridCallable
{
    private byte[] image;
    private String format;
    private Integer angle = 0;


    public ImageRotateCallable(byte[] image, String format, Integer angle) {
        this.image = image;
        this.format = format;
        this.angle = angle;
    }


    @Override
    public byte[] call() throws Exception {

        String cfcPath = GridManager.rootPath + "/apiserver-inf/components/v1/api-image.cfc";
        try {
            System.out.println("Invoking Grid Service: api-image.cfc?method=rotateImage ");

            CFCProxy proxy = new CFCProxy(cfcPath, false);
            Image result = (Image) proxy.invoke("rotateImage", new Object[]{this.image, this.angle});
            return result.getImageBytes(this.format);
        }
        catch (Throwable e) {
            e.printStackTrace();
            throw new ColdFusionException("Error Invoking FileBorderService", e);
        }
    }
}
