package apiserver.apis.v1_0.status.controllers;

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
import apiserver.apis.v1_0.status.gateways.ApiStatusGateway;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


/**
 * User: mnimer
 * Date: 8/17/12
 */


@Controller
@Api(value = "/status", description = "[GENERAL]")
@RequestMapping("/status")
public class StatusController
{
    @Autowired
    @Qualifier("apiserverHealthApiGateway")
    ApiStatusGateway gateway;
    @Autowired
    ApiStatusColdFusionGateway CFGateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;


    //Autowired
    ServletContext context;

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public WebAsyncTask<Map> checkApiServerAsync(HttpServletRequest request, HttpServletResponse response)
    {
        Callable<Map> callable = new Callable<Map>()
        {
            @Override
            public Map call() throws Exception
            {
                Future<Map> result = gateway.checkApiServerAsync();
                Map m = result.get(defaultTimeout, TimeUnit.MILLISECONDS);
                return m;

            }
        };

        return new WebAsyncTask<Map>(defaultTimeout, callable);
    }


    @RequestMapping(value = "/coldfusion/health", method = RequestMethod.GET)
    public ResponseEntity<Map> checkColdfusionSync(HttpServletRequest request, HttpServletResponse response) throws  Exception
    {
        Future<Map> result = CFGateway.checkColdfusionAsync();
        Map finalResult = result.get(defaultTimeout, TimeUnit.MILLISECONDS);
        return new ResponseEntity<Map>(finalResult, HttpStatus.OK);
        //Map finalResult = CFGateway.checkColdfusionSync();
        //return new ResponseEntity<Map>(finalResult, HttpStatus.OK);
    }

}
