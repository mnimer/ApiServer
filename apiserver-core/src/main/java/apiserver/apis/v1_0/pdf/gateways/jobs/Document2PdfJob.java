package apiserver.apis.v1_0.pdf.gateways.jobs;

import apiserver.apis.v1_0.documents.model.Document;
import apiserver.core.connectors.coldfusion.jobs.CFDocumentJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by mnimer on 4/7/14.
 */
public class Document2PdfJob  extends CFDocumentJob implements Serializable
{
    private final Logger log = LoggerFactory.getLogger(Document2PdfJob.class);

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
