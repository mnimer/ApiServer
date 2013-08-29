package apiserver.apis.v1_0.images.service.drewMetadata;

import apiserver.apis.v1_0.images.models.images.ImageMetadataModel;
import apiserver.apis.v1_0.images.wrappers.CachedImage;
import apiserver.exceptions.ColdFusionException;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.springframework.integration.Message;

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
        ImageMetadataModel props = (ImageMetadataModel)message.getPayload();

        try
        {
            CachedImage cachedImage = (CachedImage)props.getCachedImage();
            String contentType = (String)props.getContentType();

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

            props.setMetadata(metadataDirectories);

            return message;
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
