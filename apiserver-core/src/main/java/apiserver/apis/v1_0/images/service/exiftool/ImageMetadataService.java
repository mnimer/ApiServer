package apiserver.apis.v1_0.images.service.exiftool;

import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.apis.v1_0.images.models.images.ImageMetadataModel;
import apiserver.apis.v1_0.images.wrappers.CachedImage;
import org.im4java.core.ETOperation;
import org.im4java.core.ExiftoolCmd;
import org.im4java.process.ArrayListOutputConsumer;
import org.springframework.integration.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/18/12
 */
public class ImageMetadataService
{
    private static String cfcPath;



    public Object metadataInfo(Message<?> message)
    {
        ImageMetadataModel props = (ImageMetadataModel)message.getPayload();

        try
        {
            CachedImage cachedImage = props.getCachedImage();

            Map metadataDirectories = new HashMap();

            ETOperation op = new ETOperation();
            //op.getTags("Filename","ImageWidth","ImageHeight","FNumber","ExposureTime","iso");
            //op.addRawArgs( "-a", "-u", "-g" );
            op.addRawArgs( "-a", "-u", "-G1", "-t" );
            op.addImage();

            // setup command and execute it (capture output)
            ArrayListOutputConsumer output = new ArrayListOutputConsumer();
            ExiftoolCmd et = new ExiftoolCmd();
            et.setOutputConsumer(output);

            et.run(op, cachedImage.getFile().getAbsolutePath());
            ArrayList<String> cmdOutput = output.getOutput();



            for (String tag : cmdOutput)
            {
                String[] _tag = tag.trim().split("\t"); //todo , not going to work. need to count spaces
                String _group = _tag[0].trim();
                String _key = _tag[1].trim();
                String _value = _tag[2].trim();


                if( !metadataDirectories.containsKey(_group) )
                {
                    metadataDirectories.put(_group, new HashMap() );
                }

                Map subMap = (Map)metadataDirectories.get( _group );
                subMap.put( _key, _value );
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



    public Object metadataClear(Message<?> message)
    {
        Map props = (Map)message.getPayload();

        try
        {
            CachedImage cachedImage = (CachedImage)props.get(ImageConfigMBeanImpl.FILE);

            Map metadataDirectories = new HashMap();

            ETOperation op = new ETOperation();
            //op.getTags("Filename","ImageWidth","ImageHeight","FNumber","ExposureTime","iso");
            //op.addRawArgs( "-a", "-u", "-g" );
            op.addRawArgs( "-all= dst.jpg" );
            op.addImage();

            // setup command and execute it (capture output)
            ArrayListOutputConsumer output = new ArrayListOutputConsumer();
            ExiftoolCmd et = new ExiftoolCmd();
            et.setOutputConsumer(output);

            et.run(op, cachedImage.getFile().getAbsolutePath());
            ArrayList<String> cmdOutput = output.getOutput();


            for (String tag : cmdOutput)
            {
                String[] _tag = tag.trim().split("\t"); //todo , not going to work. need to count spaces
                String _group = _tag[0].trim();
                String _key = _tag[1].trim();
                String _value = _tag[2].trim();


                if( !metadataDirectories.containsKey(_group) )
                {
                    metadataDirectories.put(_group, new HashMap() );
                }

                Map subMap = (Map)metadataDirectories.get( _group );
                subMap.put( _key, _value );
            }

            // send back
            // Could be a HashMap or a MultiValueMap
            Map payload = (Map) message.getPayload();
            payload.putAll(metadataDirectories);


            Map cfData = new HashMap();
            payload.put("ExifTool", cfData);


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
