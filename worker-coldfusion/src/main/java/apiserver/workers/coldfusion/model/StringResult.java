package apiserver.workers.coldfusion.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by mnimer on 6/23/14.
 */
public class StringResult implements Serializable
{
    private Stats stats;
    private String result;


    public StringResult() {
    }


    public StringResult(Stats stats, String result) {
        this.stats = stats;
        this.result = result;
    }


    public Stats getStats() {
        return stats;
    }


    public void setStats(Stats stats) {
        this.stats = stats;
    }


    public String getResult()
    {
        return result;
    }


    public void setResult(String result)
    {
        this.result = result;
    }
}
