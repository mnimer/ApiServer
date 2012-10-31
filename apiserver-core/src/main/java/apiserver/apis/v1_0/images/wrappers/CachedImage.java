package apiserver.apis.v1_0.images.wrappers;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

/**
 * User: mnimer
 * Date: 9/28/12
 */
public class CachedImage implements Serializable
{
    private String fileName;
    private String contentType;
    private Long size;
    private byte[] fileBytes;

    private BufferedImage bufferedImage;


    public CachedImage(File file)  throws IOException
    {
        if( !file.exists() || file.isDirectory() )
        {
            throw new IOException("Invalid File Reference");
        }

        fileName = file.getName();

        byte[] bytes = FileUtils.readFileToByteArray(file);
        this.setFileBytes(bytes);
    }

    public CachedImage(CommonsMultipartFile file) throws IOException
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
        outputStream = new FileOutputStream(file);
        outputStream.write( getFileBytes() );
        outputStream.close();

        file.deleteOnExit();
        return file;


        /**
         byte[] bytes = fileBytes(uploadedFile);
         File file = File.createTempFile( UUID.randomUUID().toString(), "." +fileName(uploadedFile).split("\\.")[1] );

         BufferedWriter out = new BufferedWriter(new FileWriter(file));
         int c = 0;
         while( c < bytes.length )
         {
         out.write( bytes[c] );
         c++;
         }
         out.close();
         return file;
         **/
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
