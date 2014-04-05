package apiserver.apis.v1_0.pdf.service;

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

import apiserver.apis.v1_0.pdf.PdfConfigMBean;
import apiserver.apis.v1_0.pdf.gateways.jobs.Html2PdfJob;
import apiserver.core.connectors.coldfusion.IColdFusionBridge;
import apiserver.exceptions.ColdFusionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;

import java.util.Map;

/**
 * User: mikenimer
 * Date: 9/16/13
 */
public class Html2PdfService
{
    public final Logger log = LoggerFactory.getLogger(Html2PdfService.class);

    @Autowired
    private PdfConfigMBean pdfConfigMBean;

    @Autowired
    public IColdFusionBridge coldFusionBridge;

    public void setColdFusionBridge(IColdFusionBridge coldFusionBridge)
    {
        this.coldFusionBridge = coldFusionBridge;
    }

    public Object execute(Message<?> message) throws ColdFusionException
    {

        Html2PdfJob props = (Html2PdfJob)message.getPayload();

        try
        {
            String cfcPath = pdfConfigMBean.getConvertHtmlToPdfPath();
            String method = pdfConfigMBean.getConvertHtmlToPdfMethod();

            // extract properties
            Map<String, Object> methodArgs = coldFusionBridge.extractPropertiesFromPayload(props);

            // execute
            byte[] cfcResult = (byte[])coldFusionBridge.invoke(cfcPath, method, methodArgs);
            props.setPdfBytes(cfcResult);

            return message;
        }
        catch (Throwable e)
        {
            e.printStackTrace(); //todo use logging library
            throw new RuntimeException(e);
        }
    }


}