package apiserver.core.common.interceptors;

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

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;

/**
 * User: mnimer
 * Date: 9/21/12
 */
public class TokenAccessInterceptor implements ChannelInterceptor
{
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel)
    {
        return message;
    }


    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent)
    {
        //
    }


    @Override
    public boolean preReceive(MessageChannel channel)
    {
        return true;
    }


    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel)
    {
        return message;
    }
}
