package apiserver.workers.coldfusion.model;

import java.io.Serializable;

/**
 * Created by mnimer on 6/23/14.
 */
public class ByteArrayResult implements Serializable
{
    private Stats stats;
    private byte[] bytes;


    public ByteArrayResult() {
    }


    public ByteArrayResult(Stats stats, byte[] bytes) {
        this.stats = stats;
        this.bytes = bytes;
    }


    public Stats getStats() {
        return stats;
    }


    public void setStats(Stats stats) {
        this.stats = stats;
    }


    public byte[] getBytes() {
        return bytes;
    }


    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
