package apiserver.workers.coldfusion.model;

import java.io.Serializable;

/**
 * Created by mnimer on 6/23/14.
 */
public class Stats implements Serializable
{
    private long executionTime;


    public Stats() {
    }


    public Stats(long executionTime) {
        this.executionTime = executionTime;
    }


    public long getExecutionTime() {
        return executionTime;
    }


    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }
}
