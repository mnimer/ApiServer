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

/**
 * User: mnimer
 * Date: 10/19/12
 */
public interface PdfConfigMBean
{
    public String getConvertHtmlToPdfPath();
    public void setConvertHtmlToPdfPath(String path);

    public String getConvertHtmlToPdfMethod();
    public void setConvertHtmlToPdfMethod(String method);


    public String getConvertUrlToPdfPath();
    public void setConvertUrlToPdfPath(String path);

    public String getConvertUrlToPdfMethod();
    public void setConvertUrlToPdfMethod(String method);

    public String getConvertPptToPdfPath();
    public void setConvertPptToPdfPath(String path);

    public String getConvertPptToPdfMethod();
    public void setConvertPptToPdfMethod(String method);

    public String getConvertWordToPdfPath();
    public void setConvertWordToPdfPath(String path);

    public String getConvertWordToPdfMethod();
    public void setConvertWordToPdfMethod(String method);
}
