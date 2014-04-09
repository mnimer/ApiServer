package apiserver.apis.v1_0.pdf.gateways.jobs;

import apiserver.apis.v1_0.documents.model.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mnimer on 4/7/14.
 */
public class Word2PdfJob
{
    private final Logger log = LoggerFactory.getLogger(Ppt2PdfJob.class);

    private String documentId;
    private Document wordFile;
    private byte[] pdfBytes;


    public String getDocumentId()
    {
        return documentId;
    }


    public void setDocumentId(String documentId)
    {
        this.documentId = documentId;
    }


    public Document getWordFile()
    {
        return wordFile;
    }


    public void setWordFile(Document wordFile)
    {
        this.wordFile = wordFile;
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
