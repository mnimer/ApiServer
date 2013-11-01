package unitTests.v1_0.pdf;

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

import apiserver.apis.v1_0.pdf.gateways.PdfHtmlGateway;
import apiserver.apis.v1_0.pdf.gateways.jobs.PdfHtmlJob;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * User: mikenimer
 * Date: 9/16/13
 */
public class ConvertHtmlToPDFTest
{
    public final Logger log = LoggerFactory.getLogger(ConvertHtmlToPDFTest.class);

    //@Autowired
    private PdfHtmlGateway gateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;



    public void convertHtmlToPdf()
    {
        try
        {
            PdfHtmlJob args = new PdfHtmlJob();
            args.setHtml("<b>Hello World</b>");

            Future<Map> resultFuture = gateway.convertHtmlToPdf(args);
            Object result = resultFuture.get( defaultTimeout, TimeUnit.MILLISECONDS );

            Assert.assertTrue(result != null);
            Assert.assertTrue(result instanceof byte[] );
        }
        catch (Exception ex){
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }
}
