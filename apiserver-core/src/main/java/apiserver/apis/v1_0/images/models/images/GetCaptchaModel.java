package apiserver.apis.v1_0.images.models.images;

import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/20/13
 */
public class GetCaptchaModel
{
    private String text = null;
    private Integer width = null;
    private Integer height = null;
    private Integer fontSize = null;
    private String difficulty = "medium";
    private Boolean returnAsBase64 = false;


    public String getText()
    {
        return text;
    }


    public void setText(String text)
    {
        this.text = text;
    }


    public Integer getWidth()
    {
        return width;
    }


    public void setWidth(Integer width)
    {
        this.width = width;
    }


    public Integer getHeight()
    {
        return height;
    }


    public void setHeight(Integer height)
    {
        this.height = height;
    }


    public Integer getFontSize()
    {
        return fontSize;
    }


    public void setFontSize(Integer fontSize)
    {
        this.fontSize = fontSize;
    }


    public String getDifficulty()
    {
        return difficulty;
    }


    public void setDifficulty(String difficulty)
    {
        this.difficulty = difficulty;
    }


    public Boolean getReturnAsBase64()
    {
        return returnAsBase64;
    }


    public void setReturnAsBase64(Boolean returnAsBase64)
    {
        this.returnAsBase64 = returnAsBase64;
    }
}
