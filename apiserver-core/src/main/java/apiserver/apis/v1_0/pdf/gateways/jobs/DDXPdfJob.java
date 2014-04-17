package apiserver.apis.v1_0.pdf.gateways.jobs;

import apiserver.apis.v1_0.documents.model.Document;
import apiserver.core.connectors.coldfusion.jobs.CFPdfJob;
import apiserver.core.connectors.coldfusion.services.BinaryJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mnimer on 4/17/14.
 */
public class DDXPdfJob extends CFPdfJob implements BinaryJob
{
    private final Logger log = LoggerFactory.getLogger(OptimizePdfJob.class);

    private String documentId;
    private String ddx;
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


    public String getDdx()
    {
        return ddx;
    }


    public void setDdx(String ddx)
    {
        this.ddx = ddx;
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
