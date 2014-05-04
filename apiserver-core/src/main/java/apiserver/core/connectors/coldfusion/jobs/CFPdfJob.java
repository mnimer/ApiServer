package apiserver.core.connectors.coldfusion.jobs;

import apiserver.apis.v1_0.documents.model.Document;

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
    private static final Object ALIGN = "align";
    private static final Object BOTTOMMARGIN = "bottomMargin";
    private static final Object IMAGE = "image";
    private static final Object INFO = "info";
    private static final Object ISBASE64 = "isBase64";
    private static final Object LEFTMARGIN = "leftMargin";
    private static final Object RIGHTMARGIN = "rightMargin";
    private static final Object OPACITY = "opacity";
    private static final Object SHOWONPRINT = "showOnPrint";
    private static final Object TEXT = "text";
    private static final Object NUMBERFORMAT = "numberFormat";
    private static final Object ALLOWASSEMBLY = "allowAssembly";
    private static final Object ALLOWCOPY = "allowCopy";
    private static final Object ALLOWDEGRADEDPRINTING = "allowDegradedPrinting";
    private static final Object ALLOWFILLIN = "allowFillIn";
    private static final Object ALLOWMODIFYANNOTATIONS = "allowModifyAnnotations";
    private static final Object ALLOWPRINTING = "allowPrinting";
    private static final Object ALLOWSCREENREADERS = "allowScreenReaders";
    private static final Object ALLOWSECURE = "allowSecure";
    private static final Object ENCRYPT = "encrypt";
    private static final Object NEWUSERPASSWORD = "newUserPassword";
    private static final Object NEWOWNERPASSWORD = "newOwnerPassword";
    private static final Object POSITION = "position";
    private static final Object ROTATION = "rotation";


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


    /**
     * @param algo
     */

    public void setAlgo(String algo)
    {
        this.getOptions().put(ALGO, algo);
    }


    public void setAllowAssembly(Boolean allowAssembly)
    {
        this.getOptions().put(ALLOWASSEMBLY, allowAssembly);
    }


    public void setAllowCopy(Boolean allowCopy)
    {
        this.getOptions().put(ALLOWCOPY, allowCopy);
    }


    public void setAllowDegradedPrinting(Boolean allowDegradedPrinting)
    {
        this.getOptions().put(ALLOWDEGRADEDPRINTING, allowDegradedPrinting);
    }


    public void setAllowFillIn(Boolean allowFillIn)
    {
        this.getOptions().put(ALLOWFILLIN, allowFillIn);
    }


    public void setAllowModifyAnnotations(Boolean allowModifyAnnotations)
    {
        this.getOptions().put(ALLOWMODIFYANNOTATIONS, allowModifyAnnotations);
    }


    public void setAllowPrinting(Boolean allowPrinting)
    {
        this.getOptions().put(ALLOWPRINTING, allowPrinting);
    }


    public void setAllowScreenReaders(Boolean allowScreenReaders)
    {
        this.getOptions().put(ALLOWSCREENREADERS, allowScreenReaders);
    }


    public void setAllowSecure(Boolean allowSecure)
    {
        this.getOptions().put(ALLOWSECURE, allowSecure);
    }


    public void setAlign(String align)
    {
        this.getOptions().put(ALIGN, align);
    }


    public void setBottomMargin(Integer bottomMargin)
    {
        this.getOptions().put(BOTTOMMARGIN, bottomMargin);
    }


    public void setHScale(Double hScale)
    {
        this.getOptions().put(HSCAPE, hScale);
    }


    public void setEncrypt(String encrypt)
    {
        this.getOptions().put(ENCRYPT, encrypt);
    }


    public void setImage(Document image)
    {
        this.getOptions().put(IMAGE, image);
    }


    public void setInfo(Map info)
    {
        this.getOptions().put(INFO, info);
    }


    public void setIsBase64(Boolean isBase64)
    {
        this.getOptions().put(ISBASE64, isBase64);
    }


    public void setLeftMargin(Integer leftMargin)
    {
        this.getOptions().put(LEFTMARGIN, leftMargin);
    }


    public void setNewUserPassword(String newUserPassword)
    {
        this.getOptions().put(NEWUSERPASSWORD, newUserPassword);
    }


    public void setNewOwnerPassword(String newOwnerPassword)
    {
        this.getOptions().put(NEWOWNERPASSWORD, newOwnerPassword);
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


    public void setNoJavaScripts(Boolean noJavaScripts)
    {
        this.getOptions().put(NOJAVASCRIPTS, noJavaScripts);
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


    /**
     * @param numberFormat Allowed values: lowercaseroman, numeric, uppercaseroman
     */
    public void setNumberFormat(String numberFormat)
    {
        this.getOptions().put(NUMBERFORMAT, numberFormat);
    }


    public void setOpacity(Double opacity)
    {
        this.getOptions().put(OPACITY, opacity);
    }


    public void setPages(String pages)
    {
        this.getOptions().put(PAGES, pages);
    }


    public void setPassword(String password)
    {
        this.getOptions().put(PASSWORD, password);
    }


    public void setPosition(String position)
    {
        this.getOptions().put(POSITION, position);
    }


    public void setRightMargin(Integer rightMargin)
    {
        this.getOptions().put(RIGHTMARGIN, rightMargin);
    }


    public void setRotation(Integer rotation)
    {
        this.getOptions().put(ROTATION, rotation);
    }

    public void setShowOnPrint(Boolean showOnPrint)
    {
        this.getOptions().put(SHOWONPRINT, showOnPrint);
    }


    public void setText(String text)
    {
        this.getOptions().put(TEXT, text);
    }


    public void setVScale(Double vScale)
    {
        this.getOptions().put(VSCAPE, vScale);
    }


}