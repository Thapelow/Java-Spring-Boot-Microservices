package org.goosenvi.exercises.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import jakarta.annotation.PostConstruct;
import org.goosenvi.exercises.MicroClient;
import org.goosenvi.exercises.model.Note;
import org.goosenvi.exercises.model.NoteDocument;
import org.goosenvi.exercises.model.NoteDocumentBinder;
import org.goosenvi.exercises.model.NoteDocumentRefBinder;
import org.goosenvi.exercises.model.document.Document;
import org.goosenvi.exercises.storage.NoteDocumentRepository;
import org.goosenvi.exercises.storage.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
@RestController
public class Exercise6Controller extends AbstractExerciseController {

    /**
     * The Simple Logging Facade for Java. Spring uses Logback framework.
     */
    private static final Logger log = LoggerFactory.getLogger("Exercise6Controller");

    /**
     * The autowired NoteDocumentRepository.
     */
    @Autowired
    protected NoteDocumentRepository noteDocumentRepository;

    /**
     * The autowired NoteRepository.
     */
    @Autowired
    protected NoteRepository noteRepository;

    /**
     * The default constructor.
     */
    public Exercise6Controller() {
        super();
    }

    /**
     * Log the document URL and PATH from the AbstractExerciseController.
     */
    @PostConstruct
    public void printConfig() {
        log.info("Document service, URL: " + this.documentUrl);
        log.info("Document service, document path: " + this.documentPath);
    }

    /**
     * Stores a new Note and attaches the Document references to the Note. If a Note
     * already exists only the references to the documents are stored.
     *
     * @param noteBinder a NoteDocumentRefBinder that contains information about the
     *                   Note and Document Ids
     */
    @PostMapping("exercise6/NoteDocReferences")
    public void storeNoteWithDocumentReferences(@RequestBody NoteDocumentRefBinder noteBinder) {
        Note note = null;
        Long noteId = null;
        String docId = "";
        Optional<Note> persistentNote = null;
        Iterator<String> docIdsIt = null;
        NoteDocument noteDocument = null;
        Optional<NoteDocument> persistentNoteDocument = null;
        ArrayList<String> documentIds = null;

        try {
            log.info("Exercise 6, attach Document references to an Note");

            // Check that the Note exists
            note = noteBinder.getNote();
            persistentNote = noteRepository.findNoteByNoteId(note.getNoteId());
            if (!persistentNote.isEmpty()) {
                log.info("Exercise 6, Note found in the database: " + note.getNoteId());
            } else {
                // Store the Note
                log.info("Exercise 6, Storing Note in the database, note id: " + note.getNoteId());
                noteRepository.saveAndFlush(noteBinder.getNote());
                log.info("Exercise 6, Stored Note in the database, note id: " + note.getNoteId());
            }

            // Store the document and note identifiers
            documentIds = noteBinder.getDocIds();
            docIdsIt = documentIds.iterator();
            while (docIdsIt.hasNext()) {
                log.info("Exercise 6, storing NoteDocument reference");
                docId = (String) docIdsIt.next();
                noteId = noteBinder.getNote().getNoteId();
                persistentNoteDocument = noteDocumentRepository.findNoteDocumentByNoteIdAndDocId(noteId, docId);
                if (persistentNoteDocument.isEmpty()) {
                    log.info("Exercise 6, No document reference found for note id: " + noteId + ", and doc id: "
                            + docId);
                    noteDocument = new NoteDocument(noteId, docId);
                    noteDocumentRepository.saveAndFlush(noteDocument);
                    log.info("Exercise 6, stored Note with Document reference, note id: " + noteId + ", doc id: "
                            + docId);
                } else {
                    log.info("Exercise 6, Note with Document reference already exists, note id: " + noteId
                            + ", doc id: " + docId);
                }
            }
        } catch (Exception e) {
            log.error("Exercise 6, Encountered a problem saving to the NoteRepository or the NoteDocumentRepository",
                    e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Encountered a problem saving to the NoteRepository or the NoteDocumentRepository");
        }
    }

    /**
     * Get Note information and all Document ids that are referenced by that Note.
     * If the Note does not exists an error is sent back to the client.
     *
     * @param noteId the Note identifier
     *
     * @return the Note and all Document ids in a JSON format
     */
    @GetMapping("exercise6/NoteDocReferences/{noteId}")
    public NoteDocumentRefBinder getNoteWithDocumentReferences(@PathVariable Long noteId) {
        NoteDocumentRefBinder noteBinder = null;
        Note note = null;
        Optional<Note> persistentNote = null;
        NoteDocument[] noteDocuments = null;
        String docId = "";

        try {
            log.info("Exercise 6, get note and document references, noteId: " + noteId);
            noteBinder = new NoteDocumentRefBinder();

            // Check that the Note exists
            persistentNote = noteRepository.findNoteByNoteId(noteId);
            if (persistentNote.isEmpty()) {
                throw new NoteException("Exercise 6, The note did not exist in the database, note id: " + noteId);
            }
            note = persistentNote.get();
            noteBinder.setNote(note);

            noteDocuments = noteDocumentRepository.findAllNoteDocumentsByNoteId(noteId);
            for (int i = 0; i < noteDocuments.length; i++) {
                docId = noteDocuments[i].getDocId();
                log.info("Exercise 6, Adding a document reference to the binder, docId: " + docId);
                noteBinder.addDocumentReference(docId);
            }

            log.info("Exercise 6, finished retrieving note and document references, noteId: " + noteId);
        } catch (Exception e) {
            log.error(
                    "Exercise 6, An error occurred retrieving the note and document references for note id: " + noteId,
                    e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Exercise 6, An error occurred retrieving the note and document references for note id: " + noteId);
        }

        return noteBinder;
    }

    /**
     * Retrieves the Note specified by the parameter noteId and the retrieves all
     * documents that are references by that Note. The Note and the Documents are
     * returned in a NoteDocumentBinder that Spring converts to a JSON format.
     *
     * @param noteId the Note identifier
     *
     * @return the Note and Documents in a NoteDocumentBinder JSON format.
     */
    @GetMapping("exercise6/NoteDocuments/{noteId}")
    public NoteDocumentBinder getNoteWithDocuments(@PathVariable Long noteId) {
        NoteDocumentBinder noteBinder = null;
        Optional<Note> persistentNote = null;
        Note note = null;
        NoteDocument[] noteDocuments = null;
        Document document = null;
        String docAsString = "";
        ObjectMapper mapper = null;
        String docId = "";

        try {
            log.info("Exercise 6, Retrieving Note and Documents, noteId: " + noteId);
            noteBinder = new NoteDocumentBinder();

            // Check that the Note exists
            persistentNote = noteRepository.findNoteByNoteId(noteId);
            if (persistentNote.isEmpty()) {
                throw new NoteException("Exercise 6, The note was not found in storage, note id: " + noteId);
            }
            note = persistentNote.get();
            noteBinder.setNote(note);

            // Get the Document references from the NoteDocument repository
            noteDocuments = noteDocumentRepository.findAllNoteDocumentsByNoteId(noteId);
            if (noteDocuments != null && noteDocuments.length != 0) {
                microClient = new MicroClient();
                microClient.init();
                mapper = new ObjectMapper();
                for (int i = 0; i < noteDocuments.length; i++) {
                    docId = noteDocuments[i].getDocId();
                    log.info("Exercise 6, Retrieve Document, docId: " + docId);
                    // Get the Document information from the Document Service
                    docAsString = microClient.getDataFromEndpoint(this.documentUrl + this.documentPath + "/" + docId);
                    document = mapper.readValue(docAsString, Document.class);
                    noteBinder.addDocument(document);
                    log.info("Exercise 6, Document retrieved and added to the binder, docId: " + docId);
                }
            }
        } catch (WebClientResponseException e) {
            log.error(
                    "Exercise 6, encountered a problem fetching Note or Documents from the document service. Response message: "
                            + e.getResponseBodyAsString(),
                    e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Exercise 6, encountered a problem fetching Note or Documents from the document service");
        } catch (Exception e) {
            log.error("Exercise 6, encountered a problem fetching Note or Documents from the document service", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Exercise 6, encountered a problem fetching Note or Documents from the document service");
        }

        return noteBinder;
    }
}