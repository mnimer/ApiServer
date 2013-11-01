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
import apiserver.apis.v1_0.images.gateways.jobs.images.FileInfoJob;
import apiserver.core.connectors.coldfusion.IColdFusionBridge;
import apiserver.exceptions.ColdFusionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import java.util.Map;

/**
 * User: mnimer
 * Date: 9/18/12
 */
public class ImageInfoService
{
    @Autowired
    public ImageConfigMBean imageConfigMBean;

    @Autowired
    public IColdFusionBridge coldFusionBridge;

    public void setColdFusionBridge(IColdFusionBridge coldFusionBridge)
    {
        this.coldFusionBridge = coldFusionBridge;
    }

    public Object execute(Message<?> message) throws ColdFusionException
    {

        FileInfoJob props = (FileInfoJob)message.getPayload();

        try
        {
            String cfcPath = imageConfigMBean.getImageInfoPath();
            String method = imageConfigMBean.getImageInfoMethod();
            // extract properties
            Map<String, Object> methodArgs = coldFusionBridge.extractPropertiesFromPayload(props);
            methodArgs.put("image", props.getDocument().getFileBytes());
            methodArgs.put("contentType", props.getDocument().getContentType());
            methodArgs.put("name", props.getDocument().getFileName());

            // execute
            Object cfcResult = coldFusionBridge.invoke(cfcPath, method, methodArgs);

            MessageBuilder _message = MessageBuilder.withPayload(cfcResult).copyHeaders(message.getHeaders());
            _message.copyHeaders(message.getHeaders());
            return _message.build();
        }
        catch (Throwable e)
        {
            e.printStackTrace(); //todo use logging library
            throw new RuntimeException(e);
        }
        finally
        {

        }
    }




}
