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
        setFile(file);
    }


    /**
     * Create the document with an uploaded file
     * @param file
     * @throws IOException
     */
    public Document(MultipartFile file) throws IOException
    {
        setFile(file);
    }


    /**
     * Create the document with an uploaded file
     * @param file
     * @throws IOException
     */
    public Document(BufferedImage file) throws IOException
    {
        setFile(file);
    }


    public void setFile(Object file) throws IOException
    {
        if( file instanceof  File )
        {
            if( !((File)file).exists() || ((File)file).isDirectory() )
            {
                throw new IOException("Invalid File Reference");
            }

            fileName = ((File)file).getName();
            this.file = file;

            byte[] bytes = FileUtils.readFileToByteArray(((File)file));
            this.setFileBytes(bytes);
            this.setSize( new Integer(bytes.length).longValue() );
        }
        else if( file instanceof MultipartFile)
        {
            fileName = ((MultipartFile)file).getOriginalFilename();
            this.setFileBytes(((MultipartFile)file).getBytes());
        }
        else if (file instanceof BufferedImage)
        {
            fileName = UUID.randomUUID().toString();

            // Convert buffered reader to byte array
            String _mime = this.getContentType().substring(this.getContentType().lastIndexOf('/')+1);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write( (BufferedImage)file, _mime, byteArrayOutputStream );
            byteArrayOutputStream.flush();
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            this.setFileBytes(imageBytes);
        }
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
