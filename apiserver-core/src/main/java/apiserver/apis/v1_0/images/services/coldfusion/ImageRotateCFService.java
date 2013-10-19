package apiserver.apis.v1_0.images.services.coldfusion;

import apiserver.apis.v1_0.images.ImageConfigMBean;
import apiserver.apis.v1_0.images.models.images.FileRotateModel;
import apiserver.core.connectors.coldfusion.IColdFusionBridge;
import apiserver.exceptions.ColdFusionException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * User: mikenimer
 * Date: 8/26/13
 */
public class ImageRotateCFService
{
    public final Logger log = LoggerFactory.getLogger(ImageRotateCFService.class);


    private static String cfcPath;

    @Autowired
    private ImageConfigMBean imageConfigMBean;

    @Autowired
    public IColdFusionBridge coldFusionBridge;
    public void setColdFusionBridge(IColdFusionBridge coldFusionBridge)
    {
        this.coldFusionBridge = coldFusionBridge;
    }

    public Object execute(Message<?> message) throws ColdFusionException
    {
        FileRotateModel props = (FileRotateModel)message.getPayload();

        try
        {
            cfcPath = imageConfigMBean.getImageRotatePath();
            String method = imageConfigMBean.getImageRotateMethod();

            // extract properties
            Map<String, Object> methodArgs = coldFusionBridge.extractPropertiesFromPayload(props);


            // execute
            Object cfcResult = coldFusionBridge.invoke(cfcPath, method, methodArgs);

            // strip out the base64 string from the json packet
            ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String img = mapper.readValue((String)cfcResult, String.class);

            if( cfcResult instanceof String )
            {
                props.setBase64File(img);
            }
            else if( cfcResult instanceof BufferedImage )
            {
                props.setProcessedFileBytes((BufferedImage)cfcResult);
            }

            return props;
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