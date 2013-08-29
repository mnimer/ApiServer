package apiserver.apis.v1_0.images.transformers;

import apiserver.apis.v1_0.images.models.ImageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
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

        if( payload instanceof ImageModel )
        {
            if( ((ImageModel)payload).getProcessedFile() == null && ((ImageModel)payload).getBase64File() != null )
            {
                String base64 = ((ImageModel)payload).getBase64File();
                if( base64 == null )
                {
                    throw new RuntimeException("No image for processing found");
                }


                try
                {
                    BufferedImage image = null;
                    byte[] imageByte;
                    try {
                        BASE64Decoder decoder = new BASE64Decoder();
                        imageByte = decoder.decodeBuffer(base64);
                        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                        image = ImageIO.read(bis);
                        bis.close();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    ((ImageModel)payload).setProcessedFile(image);
                }
                catch (Exception ex){
                    throw new RuntimeException(ex);
                }
            }
        }

        return message;
    }

}
