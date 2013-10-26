package apiserver.apis.v1_0.documents.model;

import org.apache.commons.io.FileUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.util.Date;
import java.util.UUID;

/**
 * Wrapper object for item stored in Persistence Layer and Cache Layers
 *
 * User: mnimer
 * Date: 9/28/12
 */
public class Document implements Serializable
{
    private String id;
    private String fileName;
    private String contentType;
    private Long size;
    private Object file;
    private byte[] fileBytes;
    // Metadata
    private String[] tags;


    /**
     * Create the document with a local file
     * @param file
     * @throws IOException
     */
    public Document(File file)  throws IOException
    {
        if( !file.exists() || file.isDirectory() )
        {
            throw new IOException("Invalid File Reference");
        }

        fileName = file.getName();
        this.file = file;

        byte[] bytes = FileUtils.readFileToByteArray(file);
        this.setFileBytes(bytes);
        this.setSize( new Integer(bytes.length).longValue() );
    }


    /**
     * Create the document with an uploaded file
     * @param file
     * @throws IOException
     */
    public Document(MultipartFile file) throws IOException
    {
        fileName = file.getOriginalFilename();
        this.setFileBytes(file.getBytes());
    }


    /**
     * Create the document with an uploaded file
     * @param file
     * @throws IOException
     */
    public Document(BufferedImage file) throws IOException
    {
        fileName = UUID.randomUUID().toString();
        byte[] imageBytes = ((DataBufferByte) file.getData().getDataBuffer()).getData();
        this.setFileBytes(imageBytes);
    }


    public String getId()
    {
        return id;
    }


    public void setId(String id)
    {
        this.id = id;
    }


    public String getFileName()
    {
        return fileName;
    }


    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }


    public String getContentType()
    {
        return contentType;
    }


    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }


    public Long getSize()
    {
        return size;
    }


    public void setSize(Long size)
    {
        this.size = size;
    }


    public byte[] getFileBytes()
    {
        return fileBytes;
    }


    public void setFileBytes(byte[] fileBytes)
    {
        this.fileBytes = fileBytes;
    }


    public String[] getTags()
    {
        return tags;
    }


    public void setTags(String[] tags)
    {
        this.tags = tags;
    }


    /**
     * Convert the internal file byte[] array back into a generic File to return.
     * @return
     * @throws IOException
     */
    public File getFile() throws IOException
    {
        if( this.file != null && this.file instanceof File && ((File)file).exists() )
        {
            return (File)this.file;
        }


        FileOutputStream outputStream = null;
        String filePath = System.getProperty("java.io.tmpdir") +"/" + getId() +"." +getFileName().split("\\.")[1];

        file = new File(filePath);
        FileUtils.writeByteArrayToFile((File)file, getFileBytes());

        ((File)file).deleteOnExit();
        return (File)this.file;
    }

}
