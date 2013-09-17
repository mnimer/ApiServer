package apiserver.apis.v1_0.pdf.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikenimer
 * Date: 9/16/13
 */
public class PdfHtmlModel extends PdfModel
{
    public final Logger log = LoggerFactory.getLogger(PdfHtmlModel.class);

    private String html;


    public String getHtml()
    {
        return html;
    }


    public void setHtml(String html)
    {
        this.html = html;
    }
}
