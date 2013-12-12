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

    @ManagedAttribute(description = "get convert html to PDF cfc path")
    public String getConvertHtmlToPdfPath()
    {
        return convertHTMLPath;
    }


    @ManagedAttribute(description = "get convert html to PDF cfc path", persistPolicy = "OnUpdate")
    public void setConvertHtmlToPdfPath(String convertHTMLPath)
    {
        this.convertHTMLPath = convertHTMLPath;
    }


    @ManagedAttribute(description = "get convert html to PDF HTTP method")
    public String getConvertHtmlToPdfMethod()
    {
        return convertHTMLMethod;
    }


    @ManagedAttribute(description = "get convert html to PDF cfc method", persistPolicy = "OnUpdate")
    public void setConvertHtmlToPdfMethod(String convertHTMLMethod)
    {
        this.convertHTMLMethod = convertHTMLMethod;
    }
}
