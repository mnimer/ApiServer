package apiserver.workers.coldfusion.model;

/**
 * Created by mnimer on 7/3/14.
 */
public class FileByteWrapper
{
    private String name;
    private byte[] bytes;


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }


    public byte[] getBytes()
    {
        return bytes;
    }


    public void setBytes(byte[] bytes)
    {
        this.bytes = bytes;
    }
}
