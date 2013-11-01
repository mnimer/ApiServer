package apiserver.apis.v1_0.images.services.coldfusion;

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

import apiserver.apis.v1_0.images.ImageConfigMBean;
import apiserver.core.connectors.coldfusion.IColdFusionBridge;
import apiserver.exceptions.ColdFusionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/18/12
 */
public class ImageDrawingCFService
{

    private static String cfcPath;

    @Autowired
    private ImageConfigMBean imageConfigMBean;

    @Autowired
    public IColdFusionBridge coldFusionBridge;

    public void setColdFusionBridge(IColdFusionBridge coldFusionBridge)
    {
        this.coldFusionBridge = coldFusionBridge;
    }

    public Object imageBorderHandler(Message<?> message) throws ColdFusionException
    {
        Map props = (Map)message.getPayload();

        try
        {
            cfcPath = imageConfigMBean.getImageBorderPath();
            String method = imageConfigMBean.getImageBorderMethod();
            Map<String, Object> methodArgs = new HashMap();
            Map cfcResult = (Map)coldFusionBridge.invoke(cfcPath, method, methodArgs);


            Message<?> _message = MessageBuilder.withPayload(cfcResult).copyHeaders(message.getHeaders()).build();
            return _message;
        }
        catch (Throwable e)
        {
            e.printStackTrace(); //todo use logging library
            throw new RuntimeException(e);
        }
    }


    public Object imageDrawTextHandler(Message<?> message) throws ColdFusionException
    {
        Map props = (Map)message.getPayload();

        try
        {
            cfcPath = imageConfigMBean.getImageTextPath();
            String method = imageConfigMBean.getImageTextMethod();
            Map<String, Object> methodArgs = coldFusionBridge.extractPropertiesFromPayload(props);
            Map cfcResult = (Map)coldFusionBridge.invoke(cfcPath, method, methodArgs);


            Message<?> _message = MessageBuilder.withPayload(cfcResult).copyHeaders(message.getHeaders()).build();
            return _message;
        }
        catch (Throwable e)
        {
            e.printStackTrace(); //todo use logging library
            throw new RuntimeException(e);
        }
    }
}
