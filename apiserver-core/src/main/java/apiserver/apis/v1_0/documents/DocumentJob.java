package apiserver.apis.v1_0.documents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikenimer
 * Date: 10/24/13
 */
public class DocumentJob
{
    private final Logger log = LoggerFactory.getLogger(DocumentJob.class);

    private String documentId;
    private Object file;


    public String getDocumentId()
    {
        return documentId;
    }


    public void setDocumentId(String documentId)
    {
        this.documentId = documentId;
    }


    public Object getFile()
    {
        return file;
    }


    public void setFile(Object file)
    {
        this.file = file;
    }
}
