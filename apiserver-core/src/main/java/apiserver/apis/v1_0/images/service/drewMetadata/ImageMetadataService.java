package apiserver.apis.v1_0.images.service.drewMetadata;

import apiserver.ApiServerConstants;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.apis.v1_0.images.wrappers.CachedImage;
import apiserver.exceptions.ColdFusionException;
import apiserver.exceptions.MessageConfigException;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.springframework.integration.Message;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/18/12
 */
public class ImageMetadataService
{

    public Object imageMetadataHandler(Message<?> message) throws ColdFusionException
    {
        Map props = (Map)message.getPayload();
        HttpServletRequest request = (HttpServletRequest)props.get(ApiServerConstants.HTTP_REQUEST);

        try
        {
            long start = System.currentTimeMillis();
            CachedImage cachedImage = (CachedImage)props.get(ImageConfigMBeanImpl.FILE);
            String contentType = (String)props.get(ImageConfigMBeanImpl.CONTENT_TYPE);

            Map metadataDirectories = new HashMap();

            Metadata metadata = ImageMetadataReader.readMetadata( cachedImage.getFile() );

            for (Directory directory : metadata.getDirectories())
            {
                Map metadataTags = new HashMap();
                metadataDirectories.put(directory.getName(), metadataTags);
                for (Tag tag : directory.getTags())
                {
                    metadataTags.put( tag.getTagName(), tag.getDescription());
                }
            }

            long end = System.currentTimeMillis();

            // Could be a HashMap or a MultiValueMap
            Map payload = (Map) message.getPayload();
            payload.putAll(metadataDirectories);


            Map cfData = new HashMap();
            cfData.put("executiontime", end - start);
            payload.put("coldfusion", cfData);


            return payload;
        }
        catch (Throwable e)
        {
            //URL location = coldfusion.runtime.NeoPageContext.class.getProtectionDomain().getCodeSource().getLocation();
            //System.out.print(location);

            e.printStackTrace(); //todo use logging library
            throw new RuntimeException(e);
        }
    }

}
