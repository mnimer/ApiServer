package unitTests.v1_0.status;

import apiserver.core.gateways.ApiGateway;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * User: mikenimer
 * Date: 6/29/13
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:apiserver-core/src/main/webapp/WEB-INF/config/application-context-test.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/flows/status/coldfusionHealth-flow.xml"})
public class ColdFusionStatusHealthTest
{
    public final Logger log = LoggerFactory.getLogger(ColdFusionStatusHealthTest.class);

    @Autowired
    private ApiGateway apiGateway;


    @Test
    public void testColdFusionHealth()
    {
        Map result = apiGateway.checkColdfusionSync();
        log.info("RESULT:\n\n" + result + "\n\n");

        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof Map);
    }
}
