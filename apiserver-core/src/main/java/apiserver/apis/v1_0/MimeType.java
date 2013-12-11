package apiserver.apis.v1_0;

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
 * User: mikenimer
 * Date: 10/28/13
 */
public enum MimeType
{
    mp2 ("audio/mpeg"),
    mp3 ("audio/mpeg"),
    mpega ("audio/mpeg"),
    mpga ("audio/mpeg"),
    wav ("audio/x-wav"),
    wax ("audio/x-ms-wax"),

    mp4 ("video/mp4"),
    avi ("video/x-msvideo"),
    m4a ("video/mp4"),
    m4b ("video/mp4"),
    m4v ("video/mp4"),
    mov ("video/quicktime"),
    mpeg ("video/mpeg"),
    mpe ("video/mpeg"),
    mpg ("video/mpeg"),

    gif ("image/gif"),
    jpeg ("image/jpeg"),
    jpg ("image/jpeg"),
    png ("image/png"),
    tiff ("image/tiff"),
    tif ("image/tiff"),

    txt ("text/plain"),
    vcf ("text/x-vcard"),
    vcs ("text/x-vcalendar"),

    xhtml ("application/xhtml+xml"),
    xls ("application/vnd.ms-excel"),
    xml ("application/xml"),
    zip ("application/zip"),
    BINARY ("application/octet-stream");

    public String contentType;

    MimeType(String contentType)
    {
        this.contentType = contentType;
    }

    public String getExtension()
    {
        return this.name();
    }

    public static MimeType getMimeType(String fileName)
    {
        String extension = fileName.substring(fileName.lastIndexOf('.')+1);

        for (MimeType mimeType : values()) {
            if( mimeType.getExtension().equalsIgnoreCase(extension) || mimeType.contentType.equalsIgnoreCase(extension) )
            {
                return mimeType;
            }
        }
        return BINARY;
    }

}
