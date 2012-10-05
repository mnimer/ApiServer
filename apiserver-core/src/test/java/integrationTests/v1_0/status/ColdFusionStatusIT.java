package integrationTests.v1_0.status;

import apiserver.apis.v1_0.status.controllers.StatusController;
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

import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/19/12
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:**/config/v1_0/apis-servlet-test.xml"})
@Profile("dev")
@Category(categories.ColdFusionTests.class)
public class ColdFusionStatusIT
{
    @Autowired
    ApplicationContext context;

    @Autowired
    public MapFactoryBean unitTestProperties;

    @Autowired
    public StatusController statusController;


    private String getUrlBase() throws Exception
    {
        String host = unitTestProperties.getObject().get("tomcatHost").toString();
        String port = unitTestProperties.getObject().get("tomcatPort").toString();
        String contextRoot = unitTestProperties.getObject().get("tomcatContextRoot").toString();

        return "http://" +host +":" +port +contextRoot;
    }


    @Test
    public void testColdFusionStatus() throws ServletException, IOException, Exception
    {

        URL url = new URL( getUrlBase() +"/v1-0/status/coldfusion/health.json");
        BufferedReader in = new BufferedReader( new InputStreamReader(url.openStream()) );


        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(in);

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
