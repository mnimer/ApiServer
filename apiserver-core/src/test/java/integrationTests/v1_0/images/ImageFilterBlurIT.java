package integrationTests.v1_0.images;

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

import integrationTests.v1_0.HttpTest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/21/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:apiserver-core/src/main/webapp/WEB-INF/config/application-context-test.xml"})
public class ImageFilterBlurIT extends HttpTest
{

    @Test
    public void testImageSize() throws Exception
    {
        int width = 500;
        int height = 296;
        String difficulty = "high";
        String fileName = "IMG_5932_sm.png";
        String url = "/rest/v1/image/filters/blur.png";

        File file = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("file", file);
        args.put("returnAsBase64", false);


        int status = 0;
        // Here we go!
        HttpResponse response = invokeHttpPost(url, file, args);
        status = response.getStatusLine().getStatusCode();


        Assert.isTrue(status == HttpStatus.SC_OK, "Upload failed with status code: " + status);

        //BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        File tempFile = new File("/Users/mikenimer/Desktop/test.png");
        /*
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        } */
        copyToFile(response.getEntity().getContent(), tempFile);
        //in.close();

    }

    private void copyToFile(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
