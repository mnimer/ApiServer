package unitTests.v1_0.status;

import apiserver.apis.v1_0.status.gateways.ApiStatusGateway;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * User: mikenimer
 * Date: 6/29/13
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:apiserver-core/src/main/webapp/WEB-INF/config/application-context-test.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/apis-servlet-test.xml"})
public class StatusTest
{
    public final Logger log = LoggerFactory.getLogger(ColdFusionStatusHealthTest.class);

    @Autowired
    private ApiStatusGateway gateway;


    @Test
    public void testApiServerHealth()
    {
        Map result = gateway.checkApiServerSync();
        log.info("RESULT:\n\n" + result + "\n\n");

        Assert.assertNotNull(result);
        Assert.assertTrue( result.get("status").toString().equals("ok") );
    }


    @Test
    public void testApiServerHealthAsync() throws Exception
    {
        Future<Map> resultFuture = gateway.checkApiServerAsync();
        Map result = resultFuture.get(10000, TimeUnit.MILLISECONDS);
        log.info("RESULT:\n\n" + result + "\n\n");

        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof Map);
        Assert.assertTrue( result.get("status").toString().equals("ok") );
    }
}
