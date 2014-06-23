package apiserver.workers.coldfusion.services.pdf;

import apiserver.workers.coldfusion.GridManager;
import apiserver.workers.coldfusion.exceptions.ColdFusionException;
import apiserver.workers.coldfusion.model.ByteArrayResult;
import apiserver.workers.coldfusion.model.Stats;
import coldfusion.cfc.CFCProxy;
import org.gridgain.grid.lang.GridCallable;

import java.util.Map;

/**
 * Created by mnimer on 6/10/14.
 */
public class HtmlToPdfCallable implements GridCallable
{

    private String html;
    private String headerHtml = "";
    private String footerHtml = "";
    private Map options;


    public HtmlToPdfCallable(String html, String headerHtml, String footerHtml, Map options) {
        this.html = html;
        this.headerHtml = headerHtml;
        this.footerHtml = footerHtml;
        this.options = options;
    }


    @Override
    public ByteArrayResult call() throws Exception {
        String cfcPath = GridManager.rootPath + "/apiserver-inf/components/v1/api-pdf-convert.cfc";
        try {
            long startTime = System.nanoTime();
            System.out.println("Invoking Grid Service: api-pdf-convert.cfc::htmlToPdf ");

            // Invoke CFC
            CFCProxy proxy = new CFCProxy(cfcPath, false);
            byte[] result = (byte[])proxy.invoke("htmlToPdf", new Object[]{this.html, this.headerHtml, this.footerHtml, this.options});

            // return the raw bytes of the pdf & stats
            long endTime = System.nanoTime();
            Stats stats = new Stats();
            stats.setExecutionTime(endTime-startTime);

            return new ByteArrayResult(stats, result);
        }
        catch (Throwable e) {
            //e.printStackTrace();
            throw new ColdFusionException("Error Invoking HTML2PDF Service on grid", e);
        }
    }
}
