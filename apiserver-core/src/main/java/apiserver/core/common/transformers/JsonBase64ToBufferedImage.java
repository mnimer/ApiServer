package apiserver.core.common.transformers;

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

import apiserver.apis.v1_0.documents.DocumentJob;
import apiserver.apis.v1_0.images.gateways.jobs.ImageDocumentJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * User: mikenimer
 * Date: 8/28/13
 */
@Component
public class JsonBase64ToBufferedImage
{
    public final Logger log = LoggerFactory.getLogger(JsonBase64ToBufferedImage.class);


    /**
     * If a base64 was returned, this transformer will convert the base64 string back to a bufferedImage
     * @param message
     * @return
     */
    @Transformer
    public Message<Map> transform(Message<Map> message)
    {
        Object payload = message.getPayload();

        if( payload instanceof DocumentJob)
        {
            try
            {
                if( ((ImageDocumentJob)payload).getBufferedImage() == null )
                {
                    throw new RuntimeException("Not Implemented Yet");
                    /****
                    String base64 = ((ImageDocumentJob)payload).getBase64File();
                    if( base64 == null )
                    {
                        throw new RuntimeException("No image for processing found");
                    }


                    try
                    {
                        try {
                            byte[] imageByte = DatatypeConverter.parseBase64Binary(base64);
                            ((DocumentJob)payload).setProcessedFileBytes(imageByte);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                    catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                     **/
                }
            }
            catch( IOException ex)
            {
                log.error(ex.getMessage(), ex);
            }
        }

        return message;
    }

}
