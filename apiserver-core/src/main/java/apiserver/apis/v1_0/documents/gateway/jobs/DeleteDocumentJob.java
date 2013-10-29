package apiserver.apis.v1_0.documents.gateway.jobs;

import apiserver.apis.v1_0.documents.DocumentJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikenimer
 * Date: 7/19/13
 */
public class DeleteDocumentJob extends DocumentJob
{
    public final Logger log = LoggerFactory.getLogger(DeleteDocumentJob.class);

    private String documentId;


    public String getDocumentId()
    {
        return documentId;
    }


    public void setDocumentId(String documentId)
    {
        this.documentId = documentId;
    }
}