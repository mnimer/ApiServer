package unitTests.v1_0.status;

import apiserver.apis.v1_0.status.gateways.ApiStatusColdFusionGateway;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * User: mikenimer
 * Date: 6/29/13
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:apiserver-core/src/main/webapp/WEB-INF/config/application-context-test.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/apis-servlet-test.xml"})
public class ColdFusionStatusHealthTest
{
    public final Logger log = LoggerFactory.getLogger(ColdFusionStatusHealthTest.class);

    @Autowired
    private ApiStatusColdFusionGateway gateway;


    @Test
    public void testColdFusionHealth()
    {
        Map result = gateway.checkColdfusionSync();
        log.info("RESULT:\n\n" + result + "\n\n");

        Assert.assertNotNull(result);
        Assert.assertEquals( 3, ((Map)result.get("coldfusion")).size() );
    }

    @Test
    public void testColdFusionHealthAsync() throws Exception
    {
        try
        {
            Future<Map> resultFuture = gateway.checkColdfusionAsync();
            Map result = (Map)resultFuture.get( 10000, TimeUnit.MILLISECONDS );

            log.info("RESULT:\n\n" + result + "\n\n");

            Assert.assertNotNull(result);
            Assert.assertTrue(result instanceof Map);
            Assert.assertEquals( 3, ((Map)result.get("coldfusion")).size() );
            Assert.assertTrue(((Map) result.get("coldfusion")).get("status").toString().equals("ok"));

        }
        catch( TimeoutException te){
            Assert.fail("Timeout Exception");
        }
        catch (Exception ex){
            ex.printStackTrace();
            Assert.fail("Fatal Exception: " +ex.getMessage());
        }

    }

    @Test
    public void testColdFusionHealthAsyncTimeout()
    {
        try
        {
            Future<Map> resultFuture = gateway.checkColdfusionAsync();
            Map result = (Map)resultFuture.get( 1, TimeUnit.MILLISECONDS );
            Assert.fail("Expected ASYNC timeout");
        }
        catch(TimeoutException ex){
            Assert.assertTrue( true );
        }
        catch (Exception ex){
            Assert.fail("Expected ASYNC timeout");
        }

    }
}