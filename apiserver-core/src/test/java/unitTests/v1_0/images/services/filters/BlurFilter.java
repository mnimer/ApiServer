package unitTests.v1_0.images.services.filters;

/*******************************************************************************
 Copyright (c) 2013 Mike Nimer.

 This file is part of ApiServer Project.

 The ApiServer Project is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The ApiServer Project is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with the ApiServer Project.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

import apiserver.apis.v1_0.images.gateways.jobs.ImageDocumentJob;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * User: mikenimer
 * Date: 10/28/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:apiserver-core/src/main/webapp/WEB-INF/config/application-context.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/apis-servlet.xml"})

public class BlurFilter
{
    private final Logger log = LoggerFactory.getLogger(BlurFilter.class);


    @Test
    public void testBlurFilter() throws URISyntaxException, IOException
    {
        String fileName = "sample.png";
        File file = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());
        BufferedImage bufferedImage = ImageIO.read(file);
        //run filter
        com.jhlabs.image.BlurFilter filter = new com.jhlabs.image.BlurFilter();
        BufferedImage outFile = filter.filter( bufferedImage, null );

        Assert.assertEquals(500, outFile.getWidth());
        Assert.assertEquals(296, outFile.getHeight());
    }

    @Test
    public void testBlurFilterSavedToDisk() throws URISyntaxException, IOException
    {
        String fileName = "sample.png";
        File file = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());
        BufferedImage bufferedImage = ImageIO.read(file);
        //run filter
        com.jhlabs.image.BlurFilter filter = new com.jhlabs.image.BlurFilter();
        BufferedImage outFile = filter.filter( bufferedImage, null );

        Assert.assertEquals(500, outFile.getWidth());
        Assert.assertEquals(296, outFile.getHeight());




        byte[] imageBytes2 = ((DataBufferByte) bufferedImage.getData().getDataBuffer()).getData();
        File outputFile = File.createTempFile("blur", ".png");
        outputFile.deleteOnExit();
        ImageIO.write(bufferedImage, "png", outputFile);

        BufferedImage newImage = ImageIO.read(outputFile);
        Assert.assertEquals(500, newImage.getWidth());
        Assert.assertEquals(296, newImage.getHeight());
    }

    @Test
    public void testBlurFilterPngMimeType1() throws URISyntaxException, IOException
    {
        String fileName = "sample.png";
        File file = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());
        BufferedImage bufferedImage = ImageIO.read(file);
        //run filter
        com.jhlabs.image.BlurFilter filter = new com.jhlabs.image.BlurFilter();
        BufferedImage outFile = filter.filter( bufferedImage, null );

        Assert.assertEquals(500, outFile.getWidth());
        Assert.assertEquals(296, outFile.getHeight());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write( outFile, "image/png", byteArrayOutputStream );
        byteArrayOutputStream.flush();
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();

        BufferedImage bImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
        Assert.assertEquals(500, outFile.getWidth());
        Assert.assertEquals(296, outFile.getHeight());
    }


    @Test
    public void testBlurFilterPngMimeType2() throws URISyntaxException, IOException
    {
        String fileName = "sample.png";
        File file = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());
        BufferedImage bufferedImage = ImageIO.read(file);
        //run filter
        com.jhlabs.image.BlurFilter filter = new com.jhlabs.image.BlurFilter();
        BufferedImage outFile = filter.filter( bufferedImage, null );

        Assert.assertEquals(500, outFile.getWidth());
        Assert.assertEquals(296, outFile.getHeight());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write( outFile, "png", byteArrayOutputStream );
        byteArrayOutputStream.flush();
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();

        BufferedImage bImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
        Assert.assertEquals(500, outFile.getWidth());
        Assert.assertEquals(296, outFile.getHeight());
    }

    @Ignore
    @Test
    public void testBlurFilterService()
    {
        ImageDocumentJob job = new ImageDocumentJob();
        //todo
    }
}
