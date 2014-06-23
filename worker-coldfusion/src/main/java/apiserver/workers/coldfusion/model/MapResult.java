package apiserver.workers.coldfusion.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by mnimer on 6/23/14.
 */
public class MapResult implements Serializable
{
    private Stats stats;
    private Map data;


    public MapResult() {
    }


    public MapResult(Stats stats, Map data) {
        this.stats = stats;
        this.data = data;
    }


    public Stats getStats() {
        return stats;
    }


    public void setStats(Stats stats) {
        this.stats = stats;
    }


    public Map getData() {
        return data;
    }


    public void setData(Map data) {
        this.data = data;
    }
}
