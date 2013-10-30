package apiserver.apis.v1_0.documents.services;

import apiserver.apis.v1_0.documents.DocumentJob;
import apiserver.apis.v1_0.documents.gateway.jobs.GetDocumentJob;
import apiserver.apis.v1_0.documents.model.Document;
import apiserver.core.providers.cache.IDocumentCacheProvider;
import apiserver.exceptions.MessageConfigException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;

import java.io.IOException;

/**
 * User: mnimer
 * Date: 9/27/12
 */
public class DocumentService
{

    @Autowired
    private IDocumentCacheProvider cacheProvider;


    public Message<?> getFromCache(Message<?> message) throws MessageConfigException, IOException
    {
        Object payload = message.getPayload();
        if( !(payload instanceof GetDocumentJob) || ((GetDocumentJob)payload).getDocumentId() == null ){ return message; }

        GetDocumentJob p = (GetDocumentJob) payload;
        Document document = cacheProvider.get(p.getDocumentId());
        p.setDocument(document);
        return message;
    }


    public Message<?> addToCache(Message<?> message)
    {
        if( !(message.getPayload() instanceof DocumentJob) ){ return message; }

        DocumentJob payload = (DocumentJob) message.getPayload();

        if( payload.getDocument() != null )
        {
            Document document = cacheProvider.add(payload.getDocument());
            payload.setDocument(document);
        }


        return message;
    }

    public Message<?> deleteFromCache(Message<?> message)
    {
        if( !(message.getPayload() instanceof DocumentJob) ){ return message; }

        return message;
    }

}
