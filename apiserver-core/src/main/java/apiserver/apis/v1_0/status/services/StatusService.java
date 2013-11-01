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

import org.springframework.integration.Message;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mnimer
 * Date: 9/15/12
 */
public class StatusService
{

    public Map<String, Object> healthCheck(Message<?> message)
    {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("status", "ok");
        props.put("timestamp", new Date().getTime());
        return props;
    }
}
