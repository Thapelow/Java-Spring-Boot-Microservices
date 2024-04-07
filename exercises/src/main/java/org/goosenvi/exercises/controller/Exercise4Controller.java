package org.goosenvi.exercises.controller;

import java.util.ArrayList;
import java.util.List;

import org.goosenvi.exercises.model.Note;
import org.goosenvi.exercises.storage.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class Exercise4Controller {

    /**
     * The Simple Logging Facade for Java. Spring uses Logback framework.
     */
    private static final Logger log = LoggerFactory.getLogger(Exercise4Controller.class);

    /**
     * The Autowired Note repository.
     */
    @Autowired
    NoteRepository noteRepository;

    /**
     * The exception handler form exercise 3.
     *
     * Spring's general procedure to send response exceptions to calling
     * applications. Reads the message and status from the application level
     * exception.
     *
     * @param rse the ResponseStatusException that contains status code, message and
     *            exception.
     * @return a ResponseEntity with HTTP status code, body and header.
     */
    @ExceptionHandler
    public ResponseEntity<String> handle(ResponseStatusException rse) {
        return new ResponseEntity<String>(rse.getMessage(), rse.getStatusCode());
    }

    /**
     * POST methods that add the Note received from the REST call to the database
     * and logs the Note information.
     *
     * @param note the Note to store (received by Spring as a JSON Note object in
     *             the HTTP body)
     */
    @PostMapping("/exercise4/note")
    public void storeNote(@RequestBody Note note) {

        try {
            log.info("Exercise 4, POST mapping received, Note information: " + note.noteInformation());
            note = noteRepository.saveAndFlush(note);
            noteRepository.flush();
            log.info("Exercise 4, Note stored in the database. Note information: " + note.noteInformation());
        } catch (Exception e) {
            log.error("Exercise 4, error storing the note to the database", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Error storing the note");
        }
    }

    /**
     * GET method that retrieves all notes from the database and log the number of
     * notes found.
     *
     * @return the Notes found in the database
     */
    @GetMapping("/exercise4/notes")
    public List<Note> getNotes() {
        List<Note> notes = new ArrayList<Note>();
        try {
            log.info("Exercise 4, retrieving all notes from the database");
            notes = noteRepository.findAll();
            log.info("Exercise 4, retrieved " + notes.size() + " notes from the database");
        } catch (Exception e) {
            log.error("Exercise 4, error retrieving the notes from the database", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Error retrieving the notes");
        }

        return notes;
    }
//
    /**
     * GET method that retrieves all notes with a specific header.
     *
     * @param header the note header specified in the URL
     *
     * @return all notes found with the specified header
     */
    @GetMapping("/exercise4/notes/{header}")
    public Note[] getNotesByHeader(@PathVariable String header) {
        Note[] notes = null;
        try {
            log.info("Exercise 4, Retrieving notes with header: " + header);
            notes = noteRepository.findNotesByHeader(header);
            log.info("Exercise 4, Retrieved " + notes.length + " notes with header: " + header);
        } catch (Exception e) {
            log.error("Exercise 4, error retrieving the notes from the database with header: " + header, e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Error retrieving the notes with header: " + header);
        }

        return notes;
    }
}