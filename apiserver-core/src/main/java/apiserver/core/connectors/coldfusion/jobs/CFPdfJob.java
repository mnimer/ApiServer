package apiserver.core.connectors.coldfusion.jobs;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mnimer on 4/13/14.
 */
public class CFPdfJob implements Serializable
{

    private static final String ALGO = "algo";
    private static final String PAGES = "pages";
    private static final String VSCAPE = "vscape";
    private static final String HSCAPE = "hscape";
    private static final String NOATTACHMENTS = "noAttachments";
    private static final String NOCOMMENTS = "noComments";
    private static final String NOFONTS = "noFonts";
    private static final String NOJAVASCRIPTS = "noJavascripts";
    private static final String NOLINKS = "noLinks";
    private static final String NOMETADATA = "noMetadata";
    private static final String NOTHUMBNAILS = "noThumbnails";
    private static final String PASSWORD = "password";


    // Map of options to pass through, will be set with an AttributeCollection argument.
    private Map options = new HashMap();

    /**
     * Options
     */
    public Map getOptions()
    {
        return this.options;
    }

    public void setOptions(Map _options)
    {
        this.options = _options;
    }

    public void setAlgo(String algo)
    {
        this.getOptions().put(ALGO, algo);
    }

    public void setPages(String pages)
    {
        this.getOptions().put(PAGES, pages);
    }

    public void setVScale(int vScale)
    {
        this.getOptions().put(VSCAPE, vScale);
    }

    public void setHScale(int hScale)
    {
        this.getOptions().put(HSCAPE, hScale);
    }

    public void setNoAttachments(Boolean noAttachments)
    {
        this.getOptions().put(NOATTACHMENTS, noAttachments);
    }

    public void setNoBookmarks(Boolean noBookmarks)
    {
        this.getOptions().put(NOATTACHMENTS, noBookmarks);
    }

    public void setNoComments(Boolean noComments)
    {
        this.getOptions().put(NOCOMMENTS, noComments);
    }

    public void setNoFonts(Boolean noFonts)
    {
        this.getOptions().put(NOFONTS, noFonts);
    }

    public void setNoJavascripts(Boolean noJavascripts)
    {
        this.getOptions().put(NOJAVASCRIPTS, noJavascripts);
    }

    public void setNoLinks(Boolean noLinks)
    {
        this.getOptions().put(NOLINKS, noLinks);
    }

    public void setNoMetadata(Boolean noMetadata)
    {
        this.getOptions().put(NOMETADATA, noMetadata);
    }

    public void setNoThumbnails(Boolean noThumbnails)
    {
        this.getOptions().put(NOTHUMBNAILS, noThumbnails);
    }

    public void setPassword(String password)
    {
        this.getOptions().put(PASSWORD, password);
    }

}
