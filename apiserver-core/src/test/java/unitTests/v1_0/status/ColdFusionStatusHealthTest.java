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

import apiserver.apis.v1_0.status.gateways.ApiStatusColdFusionGateway;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/apis-servlet-test.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/flows/status/coldFusionHealth-flow.xml"})
public class ColdFusionStatusHealthTest
{

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;

    @Autowired
    private ApiStatusColdFusionGateway gateway;


    @Test
    public void testColdFusionHealth()
    {
        Map result = gateway.checkColdfusionSync();
        //log.info("RESULT:\n\n" + result + "\n\n");

        Assert.assertNotNull(result);
        Assert.assertEquals( 3, ((Map)result.get("coldfusion")).size() );
    }

    @Test
    public void testColdFusionHealthAsync() throws Exception
    {
        try
        {
            Future<Map> resultFuture = gateway.checkColdfusionAsync();
            Object result = resultFuture.get( defaultTimeout, TimeUnit.MILLISECONDS );

            //log.info("RESULT:\n\n" + result.toString() + "\n\n");

            Assert.assertNotNull(result);
            Assert.assertTrue(result instanceof Map);
            Assert.assertEquals( 3, ((Map)((Map)result).get("coldfusion")).size() );
            Assert.assertTrue((  ((Map)((Map)result).get("coldfusion")).get("status").toString().equals("ok")));

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
    public void testColdFusionHealthSync() throws Exception
    {
        try
        {
            Object result = gateway.checkColdfusionSync();

            //log.info("RESULT:\n\n" + result.toString() + "\n\n");

            Assert.assertNotNull(result);
            Assert.assertTrue(result instanceof Map);
            Assert.assertEquals( 3, ((Map)((Map)result).get("coldfusion")).size() );
            Assert.assertTrue((  ((Map)((Map)result).get("coldfusion")).get("status").toString().equals("ok")));

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
