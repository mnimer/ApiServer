package apiserver.apis.v1_0.images.gateways.jobs.images;

import apiserver.apis.v1_0.images.gateways.jobs.ImageDocumentJob;
import apiserver.core.models.FileModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * User: mikenimer
 * Date: 7/21/13
 */
@Component
public class FileInfoJob extends ImageDocumentJob
{
    public final Logger log = LoggerFactory.getLogger(FileInfoJob.class);
}
