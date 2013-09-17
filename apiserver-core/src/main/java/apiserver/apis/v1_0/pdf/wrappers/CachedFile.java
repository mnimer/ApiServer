package apiserver.apis.v1_0.pdf.wrappers;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

/**
 * User: mnimer
 * Date: 9/28/12
 */
public class CachedFile implements Serializable
{
    private String fileName;
    private String contentType;
    private Long size;
    private byte[] fileBytes;

    private BufferedImage bufferedImage;


    public CachedFile(File file)  throws IOException
    {
        if( !file.exists() || file.isDirectory() )
        {
            throw new IOException("Invalid File Reference");
        }

        fileName = file.getName();

        byte[] bytes = FileUtils.readFileToByteArray(file);
        this.setFileBytes(bytes);
        this.setSize( new Integer(bytes.length).longValue() );
    }

    public CachedFile(MultipartFile file) throws IOException
    {
        fileName = file.getOriginalFilename();
        this.setFileBytes(file.getBytes());
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



    public File getFile() throws IOException
    {
        FileOutputStream outputStream = null;
        String filePath = System.getProperty("java.io.tmpdir") + "/" + UUID.randomUUID().toString() +"." +getFileName().split("\\.")[1];

        File file = new File(filePath);
        FileUtils.writeByteArrayToFile(file, getFileBytes());

        //outputStream = new FileOutputStream(file);
        //outputStream.write( getFileBytes() );
        //outputStream.close();

        file.deleteOnExit();
        return file;
    }


    public BufferedImage getBufferedImage() throws IOException
    {
        if( bufferedImage != null )
        {
            return bufferedImage;
        }
        bufferedImage = ImageIO.read( new ByteArrayInputStream(getFileBytes()) );
        return bufferedImage;

    }
}
