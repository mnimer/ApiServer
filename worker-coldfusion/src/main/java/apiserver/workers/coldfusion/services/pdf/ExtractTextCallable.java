package apiserver.workers.coldfusion.services.pdf;

import apiserver.workers.coldfusion.GridManager;
import apiserver.workers.coldfusion.exceptions.ColdFusionException;
import apiserver.workers.coldfusion.model.ByteArrayResult;
import apiserver.workers.coldfusion.model.MapResult;
import apiserver.workers.coldfusion.model.Stats;
import coldfusion.cfc.CFCProxy;
import org.gridgain.grid.lang.GridCallable;

import java.util.Map;

/**
 * Created by mnimer on 6/10/14.
 */
public class ExtractTextCallable implements GridCallable
{

    private byte[] file;
    private Map options;


    public ExtractTextCallable(byte[] file, Map options) {
        this.file = file;
        this.options = options;
    }


    @Override
    public MapResult call() throws Exception {
        String cfcPath = GridManager.rootPath + "/apiserver-inf/components/v1/api-pdf.cfc";
        try {
            long startTime = System.nanoTime();
            System.out.println("Invoking Grid Service: api-pdf.cfc::extractText ");

            // Invoke CFC
            CFCProxy proxy = new CFCProxy(cfcPath, false);
            Map result = (Map)proxy.invoke("extractText", new Object[]{this.file, this.options});

            // return the raw bytes of the pdf
            long endTime = System.nanoTime();
            Stats stats = new Stats();
            stats.setExecutionTime(endTime-startTime);

            return new MapResult(stats, result);
        }
        catch (Throwable e) {
            //e.printStackTrace();
            throw new ColdFusionException("Error Invoking ExtractText Service on grid", e);
        }
    }
}
