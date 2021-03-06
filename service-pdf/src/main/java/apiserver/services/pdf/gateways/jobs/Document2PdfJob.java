package apiserver.services.pdf.gateways.jobs;

import apiserver.services.cache.model.Document;
import apiserver.core.connectors.coldfusion.jobs.CFDocumentJob;
import apiserver.core.connectors.coldfusion.services.BinaryJob;

/**
 * Created by mnimer on 4/7/14.
 */
public class Document2PdfJob  extends CFDocumentJob implements BinaryJob
{

    private String documentId;
    private Document file;
    private byte[] pdfBytes;


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


    public byte[] getPdfBytes()
    {
        return pdfBytes;
    }


    public void setPdfBytes(byte[] pdfBytes)
    {
        this.pdfBytes = pdfBytes;
    }
}
