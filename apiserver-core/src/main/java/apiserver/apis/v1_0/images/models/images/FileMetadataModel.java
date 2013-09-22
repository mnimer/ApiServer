package apiserver.apis.v1_0.images.models.images;

import apiserver.core.models.FileModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * User: mikenimer
 * Date: 8/26/13
 */
public class FileMetadataModel extends FileModel
{
    public final Logger log = LoggerFactory.getLogger(FileMetadataModel.class);

    private Map metadata;


    public Map getMetadata()
    {
        return metadata;
    }


    public void setMetadata(Map metadata)
    {
        this.metadata = metadata;
    }
}
