package apiserver.apis.v1_0.pdf;

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

import net.sf.ehcache.Cache;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * User: mnimer
 * Date: 9/18/12
 */
@Component
@ManagedResource(objectName = "com.apiserver:name=pdfApi")
public class PdfConfigMBeanImpl implements PdfConfigMBean
{
    public static String FILE = "file";
    public static String TIME_TO_LIVE = "timeToLive";
    public static String KEY = "__key";
    public static String RESULT = "__result";
    public static String CONTENT_TYPE = "__contentType";
    public static String ORIGINAL_FILE_NAME = "__originalFileName";
    public static String NAME = "__name";
    public static String SIZE = "__size";

    private String cacheName = "pdfApiCache";
    private Map<String, Cache> imageApiCache = new HashMap<String, Cache>();

    /**
     * ColdFusion CFC Paths
     */

    //convert HTML to PDF
    private String convertHTMLPath = "api-pdf-convert.cfc?method=htmlToPdf";
    private String convertHTMLMethod = "POST";
    //convert URL to PDF
    private String convertUrlPath = "api-pdf-convert.cfc?method=urlToPdf";
    private String convertUrlMethod = "POST";
    //convert PPT to PDF
    private String convertPptPath = "api-pdf-convert.cfc?method=docToPdf";
    private String convertPptMethod = "POST";
    //convert WORD to PDF
    private String convertWordPath = "api-pdf-convert.cfc?method=docToPdf";
    private String convertWordMethod = "POST";

    public PdfConfigMBeanImpl()
    {
        initializeSampleResources();

    }


    private void initializeSampleResources()
    {
        try
        {
            //TODO see ImageConfigMBean
        }
        catch (Exception ex1)
        {
            //do nothing
        }
    }

    @ManagedAttribute(description = "get cfc PATH for HTML to PDF")
    public String getConvertHtmlToPdfPath()
    {
        return convertHTMLPath;
    }

    @ManagedAttribute(description = "set cfc PATH for HTML to PDF", persistPolicy = "OnUpdate")
    public void setConvertHtmlToPdfPath(String convertHTMLPath)
    {
        this.convertHTMLPath = convertHTMLPath;
    }

    @ManagedAttribute(description = "get HTTP method for HTML to PDF")
    public String getConvertHtmlToPdfMethod()
    {
        return convertHTMLMethod;
    }

    @ManagedAttribute(description = "set HTTP method for HTML to PDF", persistPolicy = "OnUpdate")
    public void setConvertHtmlToPdfMethod(String convertHTMLMethod)
    {
        this.convertHTMLMethod = convertHTMLMethod;
    }



    @ManagedAttribute(description = "get cfc PATH for URL to PDF")
    public String getConvertUrlToPdfPath() {
        return convertUrlPath;
    }

    @ManagedAttribute(description = "set cfc PATH for URL to PDF", persistPolicy = "OnUpdate")
    public void setConvertUrlToPdfPath(String path) {
        this.convertUrlPath = path;
    }

    @ManagedAttribute(description = "get HTTP method for URL to PDF")
    public String getConvertUrlToPdfMethod()
    {
        return convertUrlMethod;
    }

    @ManagedAttribute(description = "set HTTP method for URL to PDF", persistPolicy = "OnUpdate")
    public void setConvertUrlToPdfMethod(String convertHTMLMethod)
    {
        this.convertUrlMethod = convertUrlMethod;
    }




    @ManagedAttribute(description = "get cfc PATH for PPT to PDF")
    public String getConvertPptToPdfPath() {
        return convertPptPath;
    }

    @ManagedAttribute(description = "set cfc PATH for PPT to PDF", persistPolicy = "OnUpdate")
    public void setConvertPptToPdfPath(String path) {
        this.convertPptPath = path;
    }

    @ManagedAttribute(description = "get HTTP method for PPT to PDF")
    public String getConvertPptToPdfMethod()
    {
        return convertPptMethod;
    }

    @ManagedAttribute(description = "set HTTP method for PPT to PDF", persistPolicy = "OnUpdate")
    public void setConvertPptToPdfMethod(String convertHTMLMethod)
    {
        this.convertPptMethod = convertPptMethod;
    }




    @ManagedAttribute(description = "get cfc PATH for WORD to PDF")
    public String getConvertWordToPdfPath() {
        return convertWordPath;
    }

    @ManagedAttribute(description = "set cfc PATH for WORD to PDF", persistPolicy = "OnUpdate")
    public void setConvertWordToPdfPath(String path) {
        this.convertWordPath = path;
    }

    @ManagedAttribute(description = "get HTTP method for WORD to PDF")
    public String getConvertWordToPdfMethod()
    {
        return convertWordMethod;
    }

    @ManagedAttribute(description = "set HTTP method for WORD to PDF", persistPolicy = "OnUpdate")
    public void setConvertWordToPdfMethod(String convertHTMLMethod)
    {
        this.convertWordMethod = convertWordMethod;
    }
}
