package integrationTests.v1_0.images;

import integrationTests.v1_0.HttpTest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/21/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:**/config/v1_0/apis-servlet-test.xml"})
@Profile("dev")
@Category(categories.ColdFusionTests.class)
public class ImageInfoIT extends HttpTest
{
    @Autowired
    ApplicationContext context;


    @Test
    public void testImageSize() throws ServletException, IOException, Exception
    {
        int width = 500;
        int height = 296;
        String difficulty = "high";
        String fileName = "IMG_5932_sm.png";
        String url = "/v1-0/image-info/size.json";

        File file = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());
        Map<String, String> args = new HashMap<String, String>();
        args.put("difficulty", difficulty);


        int status = 0;
        // Here we go!
        HttpResponse response = invokeHttpPost(url, file, args);
        status = response.getStatusLine().getStatusCode();


        Assert.isTrue(status == HttpStatus.SC_OK, "Upload failed with status code: " + status);

        BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(in);

        //Assert.notNull(root.get("coldfusion"));
        Assert.isTrue(((JsonNode) root.get("width")).getIntValue() == width, "Received=" + ((JsonNode) root.get("width")).getIntValue() + " expected=" + width);
        Assert.isTrue(((JsonNode) root.get("height")).getIntValue() == height, "Received=" + ((JsonNode) root.get("height")).getIntValue() + " expected=" + height);

        in.close();

    }

    @Test
    public void testMetadata() throws ServletException, IOException, Exception
    {
        int width = 500;
        int height = 296;
        String fileName = "staff-photographer-metadata-example.jpg";
        String url = "/v1-0/image-info/metadata.json";

        File file = new File(this.getClass().getClassLoader().getSystemResource(fileName).toURI());


        int status = 0;
        // Here we go!
        HttpResponse response = invokeHttpPost(url, file, null);
        status = response.getStatusLine().getStatusCode();


        Assert.isTrue(status == HttpStatus.SC_OK, response.getStatusLine().toString() );

        BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(in);

        //Assert.notNull(root.get("coldfusion"));
        Assert.isTrue(((JsonNode) root.get("width")).getIntValue() == width, "Received=" + ((JsonNode) root.get("width")).getIntValue() + " expected=" + width); //todo, change this


        in.close();

    }

}
