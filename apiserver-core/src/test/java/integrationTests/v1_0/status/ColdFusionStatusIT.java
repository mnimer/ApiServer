package integrationTests.v1_0.status;

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
