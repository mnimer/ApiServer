package apiserver.core.swagger;

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

import com.mangofactory.swagger.EndpointComparator;
import com.wordnik.swagger.core.DocumentationEndPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * User: mikenimer
 * Date: 10/16/13
 */
@Component
public class NameEndPointComparator implements EndpointComparator
{
    private final Logger log = LoggerFactory.getLogger(NameEndPointComparator.class);

    @Override
    public int compare(DocumentationEndPoint first, DocumentationEndPoint second) {
        return first.getPath().compareTo(second.getPath());
    }
}
