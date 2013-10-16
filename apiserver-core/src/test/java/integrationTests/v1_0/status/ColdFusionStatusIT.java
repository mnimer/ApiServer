package integrationTests.v1_0.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import integrationTests.v1_0.HttpTest;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * User: mnimer
 * Date: 9/19/12
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:apiserver-core/src/main/webapp/WEB-INF/config/application-context-test.xml"})
public class ColdFusionStatusIT extends HttpTest
{

    @Test
    public void testColdFusionStatus() throws Exception
    {

        String url = "/rest/v1/status/coldfusion/health.json";
        HttpResponse response = invokeHttpGet(url, (File)null, null);

        BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        String result = IOUtils.toString(in);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree( result );

        Assert.notNull(root.get("coldfusion"));
        Assert.isTrue(  ((JsonNode)root.get("coldfusion").get("status")).textValue().equals("ok") );

        in.close();
    }
}
