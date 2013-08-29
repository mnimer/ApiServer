package apiserver.apis.v1_0.images.models.images;

import apiserver.apis.v1_0.images.models.ImageModel;

/**
 * User: mikenimer
 * Date: 7/21/13
 */
public class ImageResizeModel extends ImageModel
{
    private Integer width;
    private Integer height;
    private String interpolation = "bicubic";
    private Boolean scaleToFit = false;
    private Boolean returnAsBase64 = false;


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


    public String getInterpolation()
    {
        return interpolation;
    }


    public void setInterpolation(String interpolation)
    {
        this.interpolation = interpolation;
    }


    public Boolean getScaleToFit()
    {
        return scaleToFit;
    }


    public void setScaleToFit(Boolean scaleToFit)
    {
        this.scaleToFit = scaleToFit;
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
