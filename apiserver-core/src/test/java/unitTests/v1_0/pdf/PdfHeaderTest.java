package unitTests.v1_0.pdf;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by mnimer on 4/13/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:apiserver-core/src/main/webapp/WEB-INF/config/application-context-test.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/apis-servlet-test.xml",
        "file:apiserver-core/src/main/webapp/WEB-INF/config/v1_0/flows/pdf/addHeaderToPdf-flow.xml"})

public class PdfHeaderTest
{


    @Test
    public void addHeaderImageToPdf()
    {
        Assert.fail("Not implemented yet");
    }

    public void addHeaderTextToPdf()
    {
        Assert.fail("Not implemented yet");
    }
}
