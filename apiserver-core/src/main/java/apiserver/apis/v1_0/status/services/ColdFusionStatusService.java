package apiserver.apis.v1_0.status.services;

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

import apiserver.core.connectors.coldfusion.IColdFusionBridge;
import apiserver.exceptions.ColdFusionException;
import apiserver.exceptions.MessageConfigException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

/**
 * User: mnimer
 * Date: 9/16/12
 */
public class ColdFusionStatusService
{
    Logger log = Logger.getLogger(ColdFusionStatusService.class);

    @Autowired
    public IColdFusionBridge coldFusionBridge;
    public void setColdFusionBridge(IColdFusionBridge coldFusionBridge)
    {
        this.coldFusionBridge = coldFusionBridge;
    }


    public Object healthHandler(Message<?> message) throws ColdFusionException, MessageConfigException
    {

        try
        {
            String cfcPath = "api-status.cfc?method=health";
            String method = "GET";
            String arguments = "";
            Object cfcResult = coldFusionBridge.invoke(cfcPath, method, null);

            Message<?> _message = MessageBuilder.withPayload(cfcResult).copyHeaders(message.getHeaders()).build();
            return _message;

        }
        catch (Throwable e)
        {
            log.error(e);
            throw new RuntimeException(e);
        }
    }
}
