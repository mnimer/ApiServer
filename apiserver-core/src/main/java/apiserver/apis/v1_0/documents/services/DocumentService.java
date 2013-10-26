package apiserver.apis.v1_0.documents.services;

import apiserver.apis.v1_0.documents.DocumentJob;
import apiserver.apis.v1_0.documents.gateway.jobs.GetDocumentJob;
import apiserver.apis.v1_0.documents.model.Document;
import apiserver.apis.v1_0.images.ImageConfigMBeanImpl;
import apiserver.apis.v1_0.images.gateways.jobs.filters.MaskJob;
import apiserver.core.providers.cache.IDocumentCacheProvider;
import apiserver.exceptions.FactoryException;
import apiserver.exceptions.MessageConfigException;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
        if( !(message.getPayload() instanceof DocumentJob) ){ return message; }

        GetDocumentJob payload = (GetDocumentJob) message.getPayload();
        Document document = cacheProvider.get(payload.getDocumentId());
        payload.setDocument(document);
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
