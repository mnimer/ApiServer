package integrationTests.v1_0.images.filters;

import integrationTests.v1_0.HttpTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * User: mnimer
 * Date: 11/4/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:**/config/v1_0/apis-servlet-integration-tests.xml"})
@Profile("dev")
@Category(categories.ColdFusionTests.class)
public class BicubicScaleFilterIT extends HttpTest
{

    @Test
    public void directTest() throws Exception
    {
        throw new Exception("Not implemented yet");
    }


    @Test
    public void testFilterCache() throws Exception
    {
        throw new Exception("Not implemented yet");
    }



    @Test
    public void testFilter() throws ServletException, IOException, Exception
    {
        throw new Exception("Not implemented yet");
    }

}
