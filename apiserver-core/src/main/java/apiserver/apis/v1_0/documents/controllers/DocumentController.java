package apiserver.apis.v1_0.documents.controllers;


/*******************************************************************************
 Copyright (c) 2013 Mike Nimer.

 This file is part of ApiServer Project.

 The ApiServer Project is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The ApiServer Project is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with the ApiServer Project.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

import apiserver.apis.v1_0.documents.DocumentJob;
import apiserver.apis.v1_0.documents.gateway.DocumentGateway;
import apiserver.apis.v1_0.documents.gateway.jobs.DeleteDocumentJob;
import apiserver.apis.v1_0.documents.gateway.jobs.GetDocumentJob;
import apiserver.apis.v1_0.documents.gateway.jobs.UploadDocumentJob;
import apiserver.core.common.ResponseEntityHelper;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;

/**
 * User: mnimer
 * Date: 9/18/12
 */
@Controller
@RequestMapping("/documents")
public class DocumentController
{
    @Qualifier("documentAddGateway")
    @Autowired
    public DocumentGateway documentAddGateway;

    @Qualifier("documentDeleteGateway")
    @Autowired
    public DocumentGateway documentDeleteGateway;

    @Qualifier("documentGetGateway")
    @Autowired
    public DocumentGateway documentGetGateway;

    private
    @Value("#{applicationProperties.defaultReplyTimeout}")
    Integer defaultTimeout;


    /**
     * put document into cache, usable for future manipulations APIs
     *
     * @param uploadedFile uploaded file
     * @param tags list of metadata tags
     * @return cache ID
     */
    @ApiOperation(value = "add an document to cache", multiValueResponse = true)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public WebAsyncTask<String> addDocument(
            @ApiParam(name = "uploadedFile", required = true)
            @RequestParam(value = "uploadedFile", required = true) MultipartFile uploadedFile,

            @ApiParam(name = "tags", required = false)
            @RequestParam(required = false) String[] tags
    )
            throws InterruptedException, TimeoutException, ExecutionException
    {
        final MultipartFile _file = uploadedFile;
        final String[] _tags = tags;

        Callable<String> callable = new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {
                UploadDocumentJob job = new UploadDocumentJob(_file);
                job.setTags(_tags);

                Future<DocumentJob> imageFuture = documentAddGateway.addDocument(job);
                DocumentJob payload = imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

                return payload.getDocument().getId();
            }
        };

        return new WebAsyncTask<>(10000, callable);
    }


    /**
     * pull document out of cache
     *
     * @param documentId id of document in the persistence/cache layer
     * @return byte[] array of cached file
     */
    @ResponseBody
    @ApiOperation(value = "get an document out of cache")
    @RequestMapping(value = "/{documentId}", method = {RequestMethod.GET})
    public ResponseEntity<byte[]> getImage(
            @ApiParam(name = "documentId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52")
            @PathVariable(value = "documentId") String documentId
    ) throws InterruptedException, TimeoutException, ExecutionException, IOException
    {
        GetDocumentJob args = new GetDocumentJob();
        args.setDocumentId(documentId);

        Future<DocumentJob> imageFuture = documentGetGateway.getDocument(args);
        DocumentJob payload = imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

        return ResponseEntityHelper.processFile(
                payload.getDocument().getFileBytes(),
                payload.getDocument().getContentType(), false);

    }


    /**
     * Return the metadata and general file information about the object.
     *
     * @param documentId id of document in the persistence/cache layer
     * @return Map of metadata & file properties
     */
    @ResponseBody
    @ApiOperation(value = "Return the metadata and general file information about the object")
    @RequestMapping(value = "/{documentId}/info", method = {RequestMethod.GET})
    public ResponseEntity<Map> getDocumentInfo(
            @ApiParam(name = "documentId", required = true, defaultValue = "a3c8af38-82e3-4241-8162-28e17ebcbf52")
            @PathVariable(value = "documentId") String documentId
    ) throws InterruptedException, TimeoutException, ExecutionException, IOException
    {
        throw new RuntimeException("Not Implemented Yet: " + documentId);
    }


    /**
     * pull document out of cache
     *
     * @param documentId id of document in the persistence/cache layer
     * @return TRUE if the item was deleted successfully
     */
    @ApiOperation(value = "delete an document")
    @RequestMapping(value = "/{documentId}", method = {RequestMethod.DELETE})
    public WebAsyncTask<Boolean> deleteImage(
            @ApiParam(name = "documentId", required = true) @PathVariable(value = "documentId") String documentId)
    {
        final String _documentId = documentId;

        Callable<Boolean> callable = new Callable<Boolean>()
        {
            @Override
            public Boolean call() throws Exception
            {
                DeleteDocumentJob args = new DeleteDocumentJob();
                args.setDocumentId(_documentId);

                try
                {
                    Future<DocumentJob> imageFuture = documentDeleteGateway.deleteDocument(args);
                    DocumentJob payload = imageFuture.get(defaultTimeout, TimeUnit.MILLISECONDS);

                    return (payload != null);
                } catch (Exception ex)
                {
                    return Boolean.FALSE;
                }
            }
        };

        return new WebAsyncTask<>(defaultTimeout, callable);
    }


}
