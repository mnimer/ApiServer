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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.channel.ChannelInterceptor;

/**
 * User: mikenimer
 * Date: 7/18/13
 */
public class DebugTraceInterceptor implements ChannelInterceptor
{
    public final Logger log = LoggerFactory.getLogger(DebugTraceInterceptor.class);


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel)
    {
        //log.debug("[PRE SEND] channel:" +channel +"      message:" +message.getPayload());
        return message;
    }


    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent)
    {
        //log.debug("[POST SEND] channel:" +channel );
    }


    @Override
    public boolean preReceive(MessageChannel channel)
    {
        return true;
    }


    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel)
    {
        //log.debug("[POST RECEIVE] channel:" +channel );
        return message;
    }
}
