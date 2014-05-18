package apiserver.core.model;

import apiserver.MimeType;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by mnimer on 5/15/14.
 */
public interface IDocument
{
    public String getId();

    public File getFile() throws IOException;

    public String getFileName();

    public byte[] getFileBytes();

    public MimeType getContentType();


    public void setContentType(MimeType mimeType);

    public void setFileName(String name);
}
