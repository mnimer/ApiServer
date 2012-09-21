package integrationTests.v1_0.images;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MapFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * User: mnimer
 * Date: 9/21/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:**/config/v1_0/apis-servlet.xml"})
@Profile("dev")
@Category(categories.ColdFusionTests.class)
public class ImageInfoTests
{
    @Autowired
    ApplicationContext context;

    @Autowired
    public MapFactoryBean unitTestProperties;


    private String getUrlBase() throws Exception
    {
        String host = unitTestProperties.getObject().get("tomcatHost").toString();
        String port = unitTestProperties.getObject().get("tomcatPort").toString();
        String contextRoot = unitTestProperties.getObject().get("tomcatContextRoot").toString();

        return "http://" + host + ":" + port + contextRoot;
    }


    @Test
    public void testImageSize() throws ServletException, IOException, Exception
    {
        int width = 232;
        int height = 167;
        String difficulty = "high";
        String fileName = "testImages/IMG_5932.JPG";

        File f = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());

        String url = getUrlBase() + "/v1-0/image/info/size.json";
        PostMethod filePost = new PostMethod(url);

        Part[] parts = {new FilePart(fileName, f)};
        filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));

        HttpClient client = new HttpClient();
        int status = client.executeMethod(filePost);
        //String resultUUid = filePost.getResponseBodyAsString();
        filePost.releaseConnection();


        if (status <= 201)
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(filePost.getResponseBodyAsStream()));

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(in);

            //Assert.notNull(root.get("coldfusion"));
            //Assert.isTrue(  ((JsonNode)root.get("coldfusion").get("status")).getTextValue().equals("ok") );

            in.close();
        }
        else
        {
            System.out.println("error");
        }


    }

}
