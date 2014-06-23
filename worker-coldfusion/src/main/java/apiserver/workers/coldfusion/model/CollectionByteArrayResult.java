package apiserver.workers.coldfusion.model;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by mnimer on 6/23/14.
 */
public class CollectionByteArrayResult implements Serializable
{
    private Stats stats;
    private Collection<byte[]> files;


    public CollectionByteArrayResult() {
    }


    public CollectionByteArrayResult(Stats stats, Collection<byte[]> files) {
        this.stats = stats;
        this.files = files;
    }


    public Stats getStats() {
        return stats;
    }


    public void setStats(Stats stats) {
        this.stats = stats;
    }


    public Collection<byte[]> getFiles() {
        return files;
    }


    public void setFiles(Collection<byte[]> files) {
        this.files = files;
    }
}
