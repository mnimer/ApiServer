package apiserver.apis.v1_0;

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

    public MimeType getMimeType(String fileName)
    {
        return null;
    }

    public String getExtension(MimeType mimeType)
    {
        return mimeType.name();
    }
}
