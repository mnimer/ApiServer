package apiserver.apis.v1_0.images.service.apache_commons;

import apiserver.ApiServerConstants;
import apiserver.apis.v1_0.images.FileHelper;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.exceptions.ColdFusionException;
import apiserver.exceptions.MessageConfigException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.IImageMetadata;
import org.apache.commons.imaging.common.IImageMetadata.IImageMetadataItem;
import org.apache.commons.imaging.common.RationalNumber;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.springframework.integration.Message;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



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



            Map metadataDirectories = new HashMap();

            //BufferedImage image = Imaging;


            //BufferedImage image = Imaging.getBufferedImage( FileHelper.fileBytes(file) );
            IImageMetadata metadata = Imaging.getMetadata( FileHelper.fileBytes(file) );


            List<? extends IImageMetadataItem> items = ((JpegImageMetadata)metadata).getExif().getDirectories();
            for (int i = 0; i < items.size(); i++)
            {
                IImageMetadataItem item = items.get(i);
                Map metadataTags = new HashMap();

                if( ((TiffImageMetadata.Directory) item).type == TagInfo.DIRECTORY_TYPE_ROOT )
                {
                    metadataDirectories.put("IFD0", metadataTags);
                }
                else if( ((TiffImageMetadata.Directory) item).type == TagInfo.DIRECTORY_TYPE_EXIF)
                {
                    metadataDirectories.put("EXIF", metadataTags);
                }
                else if( ((TiffImageMetadata.Directory) item).type == TagInfo.DIRECTORY_TYPE_GPS )
                {
                    metadataDirectories.put("GPS", metadataTags);
                }
                else
                {
                    metadataDirectories.put("UNKNOWN", metadataTags);
                }


                // parse properties
                for (Object tag : ((TiffImageMetadata.Directory)item).getItems())
                {
                    String tagName = ((TiffImageMetadata.Item)tag).getTiffField().getTagName();
                    Object val = ((TiffImageMetadata.Item)tag).getTiffField().getValue();

                    if( val instanceof RationalNumber )
                    {
                        val = ((RationalNumber)val).toDisplayString();
                        metadataTags.put( tagName, val );
                    }
                    else if( val instanceof RationalNumber[] )
                    {
                        Object[] args = new Object[((RationalNumber[])val).length];
                        for( int j=0; j < ((RationalNumber[])val).length; j++)
                        {
                            args[j] = ((RationalNumber[])val)[j].toDisplayString();
                        }
                        metadataTags.put( tagName, args );
                    }
                    else
                    {
                        metadataTags.put( tagName, val );
                    }

                }

                System.out.println("    " + "item: " + item);
            }

            /**
            //Metadata metadata = ImageMetadataReader.readMetadata( (BufferedInputStream)FileHelper.getFileInputStream(file), false);
            Metadata metadata = ImageMetadataReader.readMetadata(FileHelper.getFile(file));
            for (Directory directory : metadata.getDirectories())
            {
                Map metadataTags = new HashMap();
                metadataDirectories.put(directory.getName(), metadataTags);
                for (Tag tag : directory.getTags())
                {
                    metadataTags.put( tag.getTagName(), tag);
                }
            }
             **/

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
