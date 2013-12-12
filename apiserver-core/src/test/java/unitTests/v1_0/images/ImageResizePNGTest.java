package unitTests.v1_0.images;

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

import apiserver.apis.v1_0.documents.model.Document;
import apiserver.apis.v1_0.images.gateways.images.ImageResizeGateway;
import apiserver.apis.v1_0.images.gateways.jobs.images.FileResizeJob;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * User: mikenimer
 * Date: 8/20/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:apiserver-core/src/main/webapp/WEB-INF/config/application-context-test.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/apis-servlet-test.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/flows/image/imageResize-flow.xml"})
public class ImageResizePNGTest
{
    public final Logger log = LoggerFactory.getLogger(ImageResizePNGTest.class);

    @Autowired
    private ImageResizeGateway gateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;


    static File file = null;

    @BeforeClass
    public static void beforeClass()
    {
        try
        {
            String fileName = "IMG_5932_sm.png";
            file = new File(ImageInfoTest.class.getClassLoader().getSystemResource(fileName).toURI());

        }catch (Exception ex){
            org.junit.Assert.fail(ex.getMessage());
        }
    }



    @Test
    public void testPngResize() throws ServletException, IOException, Exception
    {
        int width = 250;
        int height = 148;

        try
        {
            FileResizeJob args = new FileResizeJob();
            args.setDocument(new Document(file));
            args.setWidth(width);
            args.setHeight(height);
            args.setInterpolation("bicubic");
            args.setScaleToFit(false);

            Future<Map> resultFuture = gateway.resizeImage(args);
            FileResizeJob result = (FileResizeJob)resultFuture.get( defaultTimeout, TimeUnit.MILLISECONDS );


            BufferedImage bimg =  result.getBufferedImage();
            int imgWidth          = bimg.getWidth();
            int imgHeight         = bimg.getHeight();

            Assert.isTrue(imgWidth == width);
            Assert.isTrue(imgHeight == height);
        }
        catch (Exception ex){
            ex.printStackTrace();
            org.junit.Assert.fail(ex.getMessage());
        }

    }


    @Test
    public void testScaleToFitResize() throws ServletException, IOException, Exception
    {
        int width = 250;
        int height = 148;

        try
        {
            FileResizeJob args = new FileResizeJob();
            args.setDocument(new Document(file));
            args.setWidth(width);
            args.setHeight(height);
            args.setInterpolation("bicubic");
            args.setScaleToFit(true);

            Future<Map> resultFuture = gateway.resizeImage(args);
            FileResizeJob result = (FileResizeJob)resultFuture.get( defaultTimeout, TimeUnit.MILLISECONDS );


            BufferedImage bimg =  result.getBufferedImage();
            int imgWidth          = bimg.getWidth();
            int imgHeight         = bimg.getHeight();

            Assert.isTrue(imgWidth == width);
            Assert.isTrue(imgHeight == height);
        }
        catch (Exception ex){
            ex.printStackTrace();
            org.junit.Assert.fail(ex.getMessage());
        }


    }

}
