package apiserver.core.common.transformers;

import apiserver.apis.v1_0.documents.DocumentJob;
import apiserver.apis.v1_0.images.gateways.jobs.ImageDocumentJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * User: mikenimer
 * Date: 8/28/13
 */
@Component
public class JsonBase64ToBufferedImage
{
    public final Logger log = LoggerFactory.getLogger(JsonBase64ToBufferedImage.class);


    /**
     * If a base64 was returned, this transformer will convert the base64 string back to a bufferedImage
     * @param message
     * @return
     */
    @Transformer
    public Message<Map> transform(Message<Map> message)
    {
        Object payload = message.getPayload();

        if( payload instanceof DocumentJob)
        {
            try
            {
                if( ((ImageDocumentJob)payload).getBufferedImage() == null )
                {
                    throw new RuntimeException("Not Implemented Yet");
                    /****
                    String base64 = ((ImageDocumentJob)payload).getBase64File();
                    if( base64 == null )
                    {
                        throw new RuntimeException("No image for processing found");
                    }


                    try
                    {
                        try {
                            byte[] imageByte = DatatypeConverter.parseBase64Binary(base64);
                            ((DocumentJob)payload).setProcessedFileBytes(imageByte);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                    catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                     **/
                }
            }
            catch( IOException ex)
            {
                log.error(ex.getMessage(), ex);
            }
        }

        return message;
    }

}
