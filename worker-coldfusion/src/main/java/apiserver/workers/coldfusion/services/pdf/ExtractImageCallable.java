package apiserver.workers.coldfusion.services.pdf;

import apiserver.workers.coldfusion.GridManager;
import apiserver.workers.coldfusion.exceptions.ColdFusionException;
import apiserver.workers.coldfusion.model.CollectionResult;
import apiserver.workers.coldfusion.model.FileByteWrapper;
import apiserver.workers.coldfusion.model.Stats;
import coldfusion.cfc.CFCProxy;
import org.apache.commons.codec.binary.Base64;
import org.gridgain.grid.lang.GridCallable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Created by mnimer on 6/10/14.
 */
public class ExtractImageCallable implements GridCallable
{

    private byte[] file;
    private Map options;


    public ExtractImageCallable(byte[] file, Map options) {
        this.file = file;
        this.options = options;
    }


    @Override
    public CollectionResult call() throws Exception {
        String cfcPath = GridManager.rootPath + "/apiserver-inf/components/v1/api-pdf.cfc";
        try {
            long startTime = System.nanoTime();
            System.out.println("Invoking Grid Service: api-pdf.cfc::extractImage ");

            // covert file to base64 for transfer
            String base64File = Base64.encodeBase64String(this.file);

            // Invoke CFC
            CFCProxy proxy = new CFCProxy(cfcPath, false);
            Collection<Map> result = (Collection<Map>)proxy.invoke("extractImage", new Object[]{base64File, this.options});

            //Covert base64 strings back to file byte[]
            Collection<FileByteWrapper> files = new ArrayList();
            for (Map fileInfo : result) {
                FileByteWrapper wrapper = new FileByteWrapper();
                wrapper.setName((String)fileInfo.get("name"));
                wrapper.setBytes(Base64.decodeBase64((String) fileInfo.get("file")));

                files.add(wrapper);
            }

            // return the raw bytes of the pdf
            long endTime = System.nanoTime();
            Stats stats = new Stats();
            stats.setExecutionTime(endTime-startTime);


            return new CollectionResult(stats, files);
        }
        catch (Throwable e) {
            //e.printStackTrace();
            throw new ColdFusionException("Error Invoking ExtractImage Service on grid", e);
        }
    }
}
