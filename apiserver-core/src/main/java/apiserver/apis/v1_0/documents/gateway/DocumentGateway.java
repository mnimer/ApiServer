package apiserver.apis.v1_0.documents.gateway;

import apiserver.apis.v1_0.documents.DocumentJob;
import apiserver.apis.v1_0.documents.gateway.jobs.UploadDocumentJob;
import apiserver.apis.v1_0.documents.gateway.jobs.DeleteDocumentJob;
import apiserver.apis.v1_0.documents.gateway.jobs.GetDocumentJob;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * User: mikenimer
 * Date: 7/19/13
 */
public interface DocumentGateway
{
    Future<DocumentJob> addDocument(UploadDocumentJob args);

    Future<DocumentJob> deleteDocument(DeleteDocumentJob args);

    Future<DocumentJob> getDocument(GetDocumentJob args);
}
