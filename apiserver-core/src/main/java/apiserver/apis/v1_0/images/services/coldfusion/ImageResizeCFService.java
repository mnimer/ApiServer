package apiserver.apis.v1_0.images.services.coldfusion;

import apiserver.apis.v1_0.images.ImageConfigMBean;
import apiserver.apis.v1_0.images.gateways.jobs.images.FileResizeJob;
import apiserver.core.connectors.coldfusion.IColdFusionBridge;
import apiserver.exceptions.ColdFusionException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/18/12
 */
public class ImageResizeCFService
{

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
        FileResizeJob props = (FileResizeJob)message.getPayload();

        try
        {
            cfcPath = imageConfigMBean.getImageResizePath();
            String method = imageConfigMBean.getImageResizeMethod();
            String arguments = "";
            // extract properties
            Map<String, Object> methodArgs = coldFusionBridge.extractPropertiesFromPayload(props);


            // execute
            Object cfcResult = coldFusionBridge.invoke(cfcPath, method, methodArgs);

            // strip out the base64 string from the json packet
            ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String img = mapper.readValue((String)cfcResult, String.class);

            if( cfcResult instanceof BufferedImage )
            {
                props.setBufferedImage( ((BufferedImage) cfcResult) );
            }
            else
            {
                throw new NotImplementedException();
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
