package apiserver.core.connectors.coldfusion.services;

/**
 * Job that invokes a CFC that will return a Binary File
 * Created by mnimer on 4/17/14.
 */
public interface BinaryJob
{
    byte[] getPdfBytes();
    void setPdfBytes(byte[] pdfBytes);
}
