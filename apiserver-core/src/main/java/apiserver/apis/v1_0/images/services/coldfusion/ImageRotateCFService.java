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
import apiserver.apis.v1_0.images.gateways.jobs.images.FileRotateJob;
import apiserver.core.connectors.coldfusion.IColdFusionBridge;
import apiserver.exceptions.ColdFusionException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * User: mikenimer
 * Date: 8/26/13
 */
public class ImageRotateCFService
{
    public final Logger log = LoggerFactory.getLogger(ImageRotateCFService.class);


    private static String cfcPath;

    @Autowired
    private ImageConfigMBean imageConfigMBean;

    @Autowired
    public IColdFusionBridge coldFusionBridge;
    public void setColdFusionBridge(IColdFusionBridge coldFusionBridge)
    {
        this.coldFusionBridge = coldFusionBridge;
    }

    public Object execute(Message<?> message) throws ColdFusionException
    {
        FileRotateJob props = (FileRotateJob)message.getPayload();

        try
        {
            cfcPath = imageConfigMBean.getImageRotatePath();
            String method = imageConfigMBean.getImageRotateMethod();

            // extract properties
            Map<String, Object> methodArgs = coldFusionBridge.extractPropertiesFromPayload(props);


            // execute
            Object cfcResult = coldFusionBridge.invoke(cfcPath, method, methodArgs);

            // strip out the base64 string from the json packet
            ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String img = mapper.readValue((String)cfcResult, String.class);

            if( cfcResult instanceof BufferedImage )
            {
                props.setBufferedImage((BufferedImage) cfcResult);
            }
            else
            {
                throw new NotImplementedException();
            }

            return props;
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
