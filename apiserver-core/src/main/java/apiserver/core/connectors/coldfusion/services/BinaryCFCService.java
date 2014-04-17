package apiserver.core.connectors.coldfusion.services;

import apiserver.apis.v1_0.pdf.gateways.jobs.ExtractPdfFormJob;
import apiserver.core.connectors.coldfusion.IColdFusionBridge;
import apiserver.exceptions.ColdFusionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;

import java.util.Map;

/**
 * Created by mnimer on 4/16/14.
 */
public class BinaryCFCService
{
    @Autowired
    public IColdFusionBridge coldFusionBridge;

    private String cfcMethod = null;
    private String cfcPath = null;



    public String getCfcMethod()
    {
        return cfcMethod;
    }

    public void setCfcMethod(String cfcMethod)
    {
        this.cfcMethod = cfcMethod;
    }


    public String getCfcPath()
    {
        return cfcPath;
    }

    public void setCfcPath(String cfcPath)
    {
        this.cfcPath = cfcPath;
    }


    public void setColdFusionBridge(IColdFusionBridge coldFusionBridge)
    {
        this.coldFusionBridge = coldFusionBridge;
    }


    public Object execute(Message<?> message) throws ColdFusionException
    {

        ExtractPdfFormJob props = (ExtractPdfFormJob)message.getPayload();

        try
        {
            // extract properties
            Map<String, Object> methodArgs = coldFusionBridge.extractPropertiesFromPayload(props);

            // execute
            byte[] cfcResult = (byte[])coldFusionBridge.invoke(cfcPath, cfcMethod, methodArgs);
            props.setPdfBytes(cfcResult);

            return message;
        }
        catch (Throwable e)
        {
            e.printStackTrace(); //todo use logging library
            throw new RuntimeException(e);
        }
    }

}
