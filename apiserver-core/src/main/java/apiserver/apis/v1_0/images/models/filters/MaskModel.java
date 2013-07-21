package apiserver.apis.v1_0.images.models.filters;

import apiserver.apis.v1_0.images.models.ImageModel;
import apiserver.apis.v1_0.images.wrappers.CachedImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * User: mikenimer
 * Date: 7/14/13
 */
public class MaskModel extends ImageModel
{
    public final Logger log = LoggerFactory.getLogger(MaskModel.class);

    Object mask; //String, File, MultipartFile


    public Object getMask()
    {
        return mask;
    }

    //todo: make sure the cacheed Image Service, replaces this ID with NULL (not found) or cached Image
    public void setMask(String mask)
    {
        this.mask = mask;
    }
    public void setMask(File mask)
    {
        this.mask = mask;
    }
    public void setMask(MultipartFile mask)
    {
        this.mask = mask;
    }
    public void setMask(CachedImage mask)
    {
        this.mask = mask;
    }
}
