package apiserver.services.images.gateways.jobs.images;

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

import apiserver.ApiServerConstants;
import apiserver.services.images.gateways.jobs.ImageDocumentJob;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mikenimer
 * Date: 7/21/13
 */
public class FileRotateJob extends ImageDocumentJob
{
    private Integer angle = 0;


    public Integer getAngle()
    {
        return angle;
    }


    public void setAngle(Integer angle)
    {
        this.angle = angle;
    }



    public Map toMap()
    {
        Map props = new HashMap();
        try {
            //ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //ImageIO.write(getBufferedImage(), "png", baos);
            //props.put(IMAGE, baos.toByteArray());
            props.put(ApiServerConstants.IMAGE, getBufferedImage() );
            props.put(ApiServerConstants.CONTENT_TYPE, getDocument().getContentType() );
            props.put(ApiServerConstants.FILE_NAME, getDocument().getFileName() );
        }catch(IOException e){}
        props.put( ApiServerConstants.ANGLE, getAngle() );
        return props;
    }
}
