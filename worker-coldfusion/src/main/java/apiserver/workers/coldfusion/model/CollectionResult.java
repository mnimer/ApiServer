package apiserver.workers.coldfusion.model;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by mnimer on 6/23/14.
 */
public class CollectionResult implements Serializable
{
    private Stats stats;
    private Collection collection;


    public CollectionResult() {
    }


    public CollectionResult(Stats stats, Collection collection) {
        this.stats = stats;
        this.collection = collection;
    }


    public Stats getStats() {
        return stats;
    }


    public void setStats(Stats stats) {
        this.stats = stats;
    }


    public Collection getCollection()
    {
        return collection;
    }


    public void setCollection(Collection collection)
    {
        this.collection = collection;
    }
}
