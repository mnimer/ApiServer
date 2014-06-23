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
public class ProcessDDXCallable implements GridCallable
{

    private byte[] file;
    private String ddx;


    public ProcessDDXCallable(byte[] file, String ddx) {
        this.file = file;
        this.ddx = ddx;
    }


    @Override
    public ByteArrayResult call() throws Exception {
        String cfcPath = GridManager.rootPath + "/apiserver-inf/components/v1/api-pdf.cfc";
        try {
            long startTime = System.nanoTime();
            System.out.println("Invoking Grid Service: api-pdf.cfc::processDDX ");

            // Invoke CFC
            CFCProxy proxy = new CFCProxy(cfcPath, false);
            byte[] result = (byte[])proxy.invoke("processDDX", new Object[]{this.file, this.ddx});

            // return the raw bytes of the pdf
            long endTime = System.nanoTime();
            Stats stats = new Stats();
            stats.setExecutionTime(endTime-startTime);

            return new ByteArrayResult(stats, result);
        }
        catch (Throwable e) {
            //e.printStackTrace();
            throw new ColdFusionException("Error Invoking ProcessDDX Service on grid", e);
        }
    }
}
