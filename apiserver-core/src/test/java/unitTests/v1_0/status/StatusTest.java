package unitTests.v1_0.status;

/*******************************************************************************
 Copyright (c) 2013 Mike Nimer.

 This file is part of ApiServer Project.

 The ApiServer Project is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The ApiServer Project is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with the ApiServer Project.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

import apiserver.apis.v1_0.status.gateways.ApiStatusGateway;
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

/**
 * User: mikenimer
 * Date: 6/29/13
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:apiserver-core/src/main/webapp/WEB-INF/config/application-context-test.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/apis-servlet-test.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/flows/status/apiserverHealth-flow.xml"})
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
