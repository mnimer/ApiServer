package apiserver.apis.v1_0.pdf.gateways.jobs;

import apiserver.core.models.FileModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikenimer
 * Date: 9/16/13
 */
public class PdfHtmlJob extends FileModel
{
    public final Logger log = LoggerFactory.getLogger(PdfHtmlJob.class);

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
