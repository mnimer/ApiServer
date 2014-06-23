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
public class PopulateFormFieldsCallable implements GridCallable
{

    private byte[] file;
    private Map fields;


    public PopulateFormFieldsCallable(byte[] file, Map fields) {
        this.file = file;
        this.fields = fields;
    }


    @Override
    public ByteArrayResult call() throws Exception {
        String cfcPath = GridManager.rootPath + "/apiserver-inf/components/v1/api-pdfform.cfc";
        try {
            long startTime = System.nanoTime();
            System.out.println("Invoking Grid Service: api-pdfform.cfc::populateFormFields ");

            // Invoke CFC
            CFCProxy proxy = new CFCProxy(cfcPath, false);
            byte[] result = (byte[])proxy.invoke("populateFormFields", new Object[]{this.file, this.fields});

            // return the raw bytes of the pdf
            long endTime = System.nanoTime();
            Stats stats = new Stats();
            stats.setExecutionTime(endTime-startTime);

            return new ByteArrayResult(stats, result);
        }
        catch (Throwable e) {
            //e.printStackTrace();
            throw new ColdFusionException("Error Invoking PopulateFormFields Service on grid", e);
        }
    }
}
