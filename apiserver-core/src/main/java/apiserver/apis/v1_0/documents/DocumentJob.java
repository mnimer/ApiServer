package apiserver.apis.v1_0.documents;

import apiserver.apis.v1_0.documents.model.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mikenimer
 * Date: 10/24/13
 */
public class DocumentJob
{
    private final Logger log = LoggerFactory.getLogger(DocumentJob.class);

    private Document document;


    public Document getDocument()
    {
        return document;
    }

    public void setDocument(Document document)
    {
        this.document = document;
    }
}
