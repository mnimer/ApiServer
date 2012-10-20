package apiserver.apis.v1_0.images.routers;

import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.Router;

/**
 * User: mnimer
 * Date: 10/19/12
 */

public class MetadataRouter
{

    @Autowired
    public ImageConfigMBeanImpl imageConfigMBean;


    @Router
    public String route(Message message)
    {
        return imageConfigMBean.getMetadataLibrary();
    }

}
