package apiserver.apis.v1_0.images.gateways.jobs.filters;

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

import apiserver.apis.v1_0.images.gateways.jobs.ImageDocumentJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * User: mikenimer
 * Date: 7/14/13
 */
public class MaskJob extends ImageDocumentJob
{
    private final Logger log = LoggerFactory.getLogger(MaskJob.class);

    Object mask; //String, File, MultipartFile


    public Object getMask()
    {
        return mask;
    }

    //todo: make sure the cacheed Image Service, replaces this ID with NULL (not found) or cached Image
    public void setMask(String mask)
    {
        this.mask = mask;
    }
    public void setMask(File mask)
    {
        this.mask = mask;
    }
    public void setMask(MultipartFile mask)
    {
        this.mask = mask;
    }
}
