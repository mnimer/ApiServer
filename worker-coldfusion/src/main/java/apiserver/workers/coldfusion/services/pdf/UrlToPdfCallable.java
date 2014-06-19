package apiserver.workers.coldfusion.services.pdf;

import apiserver.workers.coldfusion.GridManager;
import apiserver.workers.coldfusion.exceptions.ColdFusionException;
import coldfusion.cfc.CFCProxy;
import coldfusion.image.Image;
import org.gridgain.grid.lang.GridCallable;

import java.util.Map;

/**
 * Created by mnimer on 6/10/14.
 */
public class UrlToPdfCallable implements GridCallable
{

    private String url;
    private Map options;


    public UrlToPdfCallable(String url, Map options) {
        this.url = url;
        this.options = options;
    }


    @Override
    public byte[] call() throws Exception {
        String cfcPath = GridManager.rootPath + "/apiserver-inf/components/v1/api-pdf-convert.cfc";
        try {
            System.out.println("Invoking Grid Service: api-pdf-convert.cfc::urlToPdf ");

            // Invoke CFC
            CFCProxy proxy = new CFCProxy(cfcPath, false);
            byte[] result = (byte[])proxy.invoke("urlToPdf", new Object[]{this.url, this.options});

            // return the raw bytes of the pdf
            return result; //todo get bytes of pdf
        }
        catch (Throwable e) {
            //e.printStackTrace();
            throw new ColdFusionException("Error Invoking URL to PDF Service on grid", e);
        }
    }
}
