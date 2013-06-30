package integrationTests.v1_0.status;

import integrationTests.v1_0.HttpTest;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * User: mnimer
 * Date: 9/19/12
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/apis-servlet-integration-tests.xml"})
public class ColdFusionStatusIT extends HttpTest
{
    @Autowired
    ApplicationContext context;



    @Test
    public void testColdFusionStatus() throws Exception
    {

        String url = "/rest/v1/status/coldfusion/health.json";
        HttpResponse response = invokeHttpGet(url, (File)null, null);

        BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));


        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree( in );

        Assert.notNull(root.get("coldfusion"));
        Assert.isTrue(  ((JsonNode)root.get("coldfusion").get("status")).getTextValue().equals("ok") );

        in.close();

        /**
       MockHttpServletRequest request = new MockHttpServletRequest();
       MockHttpServletResponse response = new MockHttpServletResponse();

       Object view = statusController.coldFusionCheck(request, response);

       Assert.isInstanceOf(ModelAndView.class, view);
       Assert.isTrue(((ModelAndView) view).getModel().get("status").toString().equals("ok"));
        **/
    }
}
