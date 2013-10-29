package apiserver.apis.v1_0.images.gateways.jobs;

import apiserver.apis.v1_0.documents.gateway.jobs.GetDocumentJob;
import apiserver.apis.v1_0.documents.model.Document;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * User: mikenimer
 * Date: 10/24/13
 */
public class ImageDocumentJob extends GetDocumentJob
{
    private final Logger log = LoggerFactory.getLogger(ImageDocumentJob.class);

    private BufferedImage bufferedImage;



    /**
     * Convert the internal byte Array back into a BufferedImage file.
     * @return BufferedImage
     * @throws java.io.IOException if the bytes are not a valid image.
     */
    public BufferedImage getBufferedImage() throws IOException
    {
        if( bufferedImage != null )
        {
            return bufferedImage;
        }

        bufferedImage = ImageIO.read(new ByteArrayInputStream(this.getDocument().getFileBytes()));
        return bufferedImage;
    }


    public void setBufferedImage(BufferedImage bufferedImage) throws IOException
    {
        this.bufferedImage = bufferedImage;

        //update file bytes
        this.getDocument().setFile(bufferedImage);
    }
}
