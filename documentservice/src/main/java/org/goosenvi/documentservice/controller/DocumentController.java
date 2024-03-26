package org.goosenvi.documentservice.controller;

import org.goosenvi.documentservice.model.Document;
import org.goosenvi.documentservice.storage.DocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class DocumentController {
    private static Logger log = LoggerFactory.getLogger(DocumentController.class);
    @Autowired
    DocumentRepository documentRepository = null;

    @ExceptionHandler
    public ResponseEntity<String> handle(ResponseStatusException rse){
        return new ResponseEntity<String>(rse.getMessage(),rse.getStatusCode());
    }
    @PostMapping("document")
    public void saveToDocument(@RequestBody Document document){
        log.info("Document before saving to database: " + document.documentInformation());
        try {
            document = documentRepository.saveAndFlush(document);
            log.info("Document after saving to the database: " + document.documentInformation());
        }catch (Exception e) {
            log.error("Error saving the to the database", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "An error occurred when saving the document to database",e);
        }
    }
    @GetMapping("document/{docId}")
    public Document getDocument(@PathVariable String docId){
        Document document = null;
        try {
            log.info("Retrieving information from the database, document Id: " + docId);
            document = documentRepository.findDocumentBydocId(docId);
            if (document == null || document.getDocId().isEmpty()){
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Document not found in the database");
            } else {
                log.info("Retrieved document from the database, document id: " + document.getDocId());
            }
        }catch (Exception e){
            log.error("An error occurred while trying to retrieve the document in the database ",e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "An error occurred while trying to retrieve the document in the database ",e);
        }
        return document;
    }
}
