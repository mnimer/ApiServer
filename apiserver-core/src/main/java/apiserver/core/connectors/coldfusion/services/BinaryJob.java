package apiserver.core.connectors.coldfusion.services;

/**
 * Created by mnimer on 4/17/14.
 */
public interface BinaryJob
{
    byte[] getPdfBytes();
    void setPdfBytes(byte[] pdfBytes);
}
