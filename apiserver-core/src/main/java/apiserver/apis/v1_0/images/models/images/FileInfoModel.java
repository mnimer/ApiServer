package apiserver.apis.v1_0.images.models.images;

import apiserver.core.models.FileModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * User: mikenimer
 * Date: 7/21/13
 */
@Component
public class FileInfoModel extends FileModel
{
    public final Logger log = LoggerFactory.getLogger(FileInfoModel.class);
}
