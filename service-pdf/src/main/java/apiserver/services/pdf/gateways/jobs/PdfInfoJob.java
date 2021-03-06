package apiserver.services.pdf.gateways.jobs;

import apiserver.services.cache.model.Document;
import apiserver.core.connectors.coldfusion.jobs.CFPdfJob;
import apiserver.core.connectors.coldfusion.services.ObjectJob;

/**
 * Created by mnimer on 4/17/14.
 */
public class PdfInfoJob extends CFPdfJob implements ObjectJob
{

    private String documentId;
    private Document file;
    private Object result;


    public String getDocumentId()
    {
        return documentId;
    }


    public void setDocumentId(String documentId)
    {
        this.documentId = documentId;
    }


    public Document getFile()
    {
        return file;
    }


    public void setFile(Document file)
    {
        this.file = file;
    }


    public Object getResult()
    {
        return result;
    }


    public void setResult(Object results)
    {
        this.result = result;
    }
}
