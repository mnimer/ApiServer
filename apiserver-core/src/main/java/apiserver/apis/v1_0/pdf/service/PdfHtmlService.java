package apiserver.apis.v1_0.pdf.service;

import apiserver.apis.v1_0.pdf.PdfConfigMBean;
import apiserver.apis.v1_0.pdf.gateways.jobs.PdfHtmlJob;
import apiserver.core.connectors.coldfusion.IColdFusionBridge;
import apiserver.exceptions.ColdFusionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import java.util.Map;

/**
 * User: mikenimer
 * Date: 9/16/13
 */
public class PdfHtmlService
{
    public final Logger log = LoggerFactory.getLogger(PdfHtmlService.class);

    @Autowired
    private PdfConfigMBean pdfConfigMBean;

    @Autowired
    public IColdFusionBridge coldFusionBridge;
    public void setColdFusionBridge(IColdFusionBridge coldFusionBridge)
    {
        this.coldFusionBridge = coldFusionBridge;
    }

    public Object execute(Message<?> message) throws ColdFusionException
    {

        PdfHtmlJob props = (PdfHtmlJob)message.getPayload();

        try
        {
            String cfcPath = pdfConfigMBean.getConvertHtmlToPdfPath();
            String method = pdfConfigMBean.getConvertHtmlToPdfMethod();
            // extract properties
            Map<String, Object> methodArgs = coldFusionBridge.extractPropertiesFromPayload(props);
            methodArgs.put("image", props.getFile());

            // execute
            Object cfcResult = coldFusionBridge.invoke(cfcPath, method, methodArgs);

            Message<?> _message = MessageBuilder.withPayload(cfcResult).copyHeaders(message.getHeaders()).build();
            return _message;
        }
        catch (Throwable e)
        {
            e.printStackTrace(); //todo use logging library
            throw new RuntimeException(e);
        }
        finally
        {

        }
    }


}
