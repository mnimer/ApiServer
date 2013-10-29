package apiserver.apis.v1_0.documents.gateway.jobs;


import apiserver.apis.v1_0.documents.DocumentJob;
import apiserver.apis.v1_0.documents.model.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * User: mikenimer
 * Date: 7/19/13
 */
public class UploadDocumentJob extends DocumentJob
{
    public final Logger log = LoggerFactory.getLogger(UploadDocumentJob.class);


    /**
     * create the object with the Multipart file that was uploaded
     * @param multipartFile uploaded file
     * @throws IOException
     */
    public UploadDocumentJob(MultipartFile multipartFile) throws IOException
    {
        Document doc = new Document(multipartFile);
        this.setDocument(doc);
    }


    /**
     * create the object with the local file, useful during unit testing.
     * @param file local file reference
     * @throws IOException
     */
    public UploadDocumentJob(File file) throws IOException
    {
        Document doc = new Document(file);
        this.setDocument(doc);
    }


    /**
     * set extra metadata
     * @param tags list of tags to describe the file
     */
    public void setTags(String[] tags)
    {
        this.getDocument().setTags(tags);
    }

}
