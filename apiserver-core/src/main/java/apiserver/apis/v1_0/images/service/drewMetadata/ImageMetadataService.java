package apiserver.apis.v1_0.images.service.drewMetadata;

import apiserver.ApiServerConstants;
import apiserver.apis.v1_0.images.FileHelper;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.exceptions.ColdFusionException;
import apiserver.exceptions.MessageConfigException;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.springframework.integration.Message;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.rmi.server.UID;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * User: mnimer
 * Date: 9/18/12
 */
public class ImageMetadataService
{
    private static String cfcPath;



    public Object imageMetadataHandler(Message<?> message) throws ColdFusionException
    {
        Map props = (Map)message.getPayload();
        HttpServletRequest request = (HttpServletRequest)props.get(ApiServerConstants.HTTP_REQUEST);

        if( cfcPath == null )
        {
            if( request == null )
            {
                throw new RuntimeException(MessageConfigException.MISSING_REQUEST_PROPERTY);
            }
            cfcPath = request.getRealPath("/WEB-INF/cfservices-inf/components/v1_0/api-image.cfc");
        }


        try
        {
            long start = System.currentTimeMillis();
            Object file = props.get(ImageConfigMBeanImpl.FILE);
            String contentType = (String)props.get(ImageConfigMBeanImpl.CONTENT_TYPE);

            Map metadataDirectories = new HashMap();


            byte[] bytes = ((DataBufferByte)((BufferedImage)file).getRaster().getDataBuffer()).getData();
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            BufferedInputStream bis = new BufferedInputStream( bais );

            //Metadata metadata = ImageMetadataReader.readMetadata( (BufferedInputStream)FileHelper.getFileInputStream(file), false);
            Metadata metadata = ImageMetadataReader.readMetadata( bis, false );


            //File tmpFile = File.createTempFile(UUID.randomUUID().toString(), ".jpg");
            //ImageIO.write((BufferedImage) file, "jpeg", tmpFile);
            //Metadata metadata = ImageMetadataReader.readMetadata(tmpFile);
            //tmpFile.deleteOnExit();

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
