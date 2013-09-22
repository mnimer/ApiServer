package unitTests.v1_0.pdf;

import apiserver.apis.v1_0.pdf.gateways.pdf.PdfHtmlGateway;
import apiserver.apis.v1_0.pdf.models.PdfHtmlModel;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PdfHtmlGateway gateway;

    private @Value("#{applicationProperties.defaultReplyTimeout}") Integer defaultTimeout;



    public void convertHtmlToPdf()
    {
        try
        {
            PdfHtmlModel args = new PdfHtmlModel();
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
