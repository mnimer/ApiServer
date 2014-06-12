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
public class ImageTextCallable implements GridCallable
{
    private byte[] image;
    private String format;
    private String text;
    private String color;
    private String fontSize;
    private String fontStyle;
    private Integer angle;
    private Integer x;
    private Integer y;


    public ImageTextCallable(byte[] image, String format, String text, String color, String fontSize, String fontStyle, Integer angle, Integer x, Integer y) {
        this.image = image;
        this.format = format;
        this.text = text;
        this.color = color;
        this.fontSize = fontSize;
        this.fontStyle = fontStyle;
        this.angle = angle;
        this.x = x;
        this.y = y;
    }


    @Override
    public byte[] call() throws Exception {
        String cfcPath = GridManager.rootPath + "/apiserver-inf/components/v1/api-image.cfc";

        try {
            System.out.println("Invoking Grid Service: api-image.cfc?method=addText ");

            CFCProxy proxy = new CFCProxy(cfcPath, false);
            Image result = (Image) proxy.invoke("addText", new Object[]{this.image, this.text, this.color, this.fontSize, this.fontStyle, this.angle, this.x, this.y});
            //System.out.println("execute Result:" + result);
            return result.getImageBytes(this.format);
        }
        catch (Throwable e) {
            e.printStackTrace();
            throw new ColdFusionException("Error Invoking FileBorderService", e);
        }

    }
}
