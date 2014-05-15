package apiserver.core.connectors.coldfusion;

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

import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mikenimer
 * Date: 3/24/13
 * Time: 1:50 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IColdFusionBridge
{

    public Object invoke(String cfcPath, String method, Map<String, Object> methodArgs_) throws Throwable;

    public Map<String, Object> extractPropertiesFromPayload(Object props)  throws IOException;

}
