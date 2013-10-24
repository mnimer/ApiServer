package apiserver.apis.v1_0.images.gateways.jobs.images;

import apiserver.apis.v1_0.images.gateways.jobs.ImageDocumentJob;
import apiserver.core.models.FileModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikenimer
 * Date: 9/16/13
 */
public class FileTextJob extends ImageDocumentJob
{
    public final Logger log = LoggerFactory.getLogger(FileTextJob.class);

    private String text;
    private String color;
    private String fontSize;
    private String fontStyle;
    private Integer angle;
    private Integer x;
    private Integer y;


    public String getText()
    {
        return text;
    }


    public void setText(String text)
    {
        this.text = text;
    }


    public String getColor()
    {
        return color;
    }


    public void setColor(String color)
    {
        this.color = color;
    }


    public String getFontSize()
    {
        return fontSize;
    }


    public void setFontSize(String fontSize)
    {
        this.fontSize = fontSize;
    }


    public String getFontStyle()
    {
        return fontStyle;
    }


    public void setFontStyle(String fontStyle)
    {
        this.fontStyle = fontStyle;
    }


    public Integer getAngle()
    {
        return angle;
    }


    public void setAngle(Integer angle)
    {
        this.angle = angle;
    }


    public Integer getX()
    {
        return x;
    }


    public void setX(Integer x)
    {
        this.x = x;
    }


    public Integer getY()
    {
        return y;
    }


    public void setY(Integer y)
    {
        this.y = y;
    }
}
