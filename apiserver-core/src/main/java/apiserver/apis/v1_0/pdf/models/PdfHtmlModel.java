package apiserver.apis.v1_0.pdf.models;

import apiserver.core.models.FileModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikenimer
 * Date: 9/16/13
 */
public class PdfHtmlModel extends FileModel
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
