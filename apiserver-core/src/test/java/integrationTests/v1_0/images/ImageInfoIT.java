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
@ContextConfiguration(locations = {
        "file:apiserver-core/src/main/webapp/WEB-INF/config/application-context-test.xml"})
public class ImageInfoIT extends HttpTest
{

    @Test
    public void testImageSize() throws Exception
    {
        int width = 500;
        int height = 296;
        String difficulty = "high";
        String fileName = "IMG_5932_sm.png";
        String url = "/rest/v1/image-info/info.json";

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

}
