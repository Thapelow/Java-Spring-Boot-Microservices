package org.goosenvi.exercises.control;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.goosenvi.exercises.model.Note;
import org.goosenvi.exercises.model.NoteDocument;
import org.goosenvi.exercises.storage.NoteDocumentRepository;
import org.goosenvi.exercises.storage.NoteRepository;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class ExerciseControllerTest {

    /**
     * The Simple Logging Facade for Java. Spring uses Logback framework.
     */
    private static final Logger log = LoggerFactory.getLogger(ExerciseControllerTest.class);

    /**
     * The autowired NoteRepository.
     */
    @Autowired
    private NoteRepository noteRepository = null;

    /**
     * The autowired NoteDocumentRepository.
     */
    @Autowired
    private NoteDocumentRepository noteDocRepository = null;

    /**
     * Creates a Note with values from the <code>TestNote</code>.
     *
     * @return a new Note with values from the <code>TestNote</code>
     */
    private Note getTestNote() {
        return new Note(TestNote.ID, TestNote.NOTE_ID, TestNote.HEADER, TestNote.BODY, TestNote.COMMENT);
    }

    /**
     * Creates a test Note and asserts that the Note has the same values.
     */
    @Test
    public void testNote() {
        Note note = null;

        log.info("Testing Note class");
        note = getTestNote();

        assertThat(TestNote.ID).isEqualTo(note.getId());
        assertThat(TestNote.NOTE_ID).isEqualTo(note.getNoteId());
        assertThat(TestNote.HEADER).isEqualTo(note.getHeader());
        assertThat(TestNote.BODY).isEqualTo(note.getBody());
        assertThat(TestNote.COMMENT).isEqualTo(note.getComment());
        log.info("Done testing Note class");
    }

    /**
     * Saves a test Note to the repository and then retrieves that Note and asserts
     * the values from the stored Note.
     */
    @Test
    public void testRepositoryStoreNote() {
        Note note = null;
        Note storedNote = null;

        log.info("Testing NoteRepository Store Note");
        note = getTestNote();
        noteRepository.saveAndFlush(note);
        storedNote = noteRepository.findById(TestNote.ID).get();

        assertThat(TestNote.ID).isEqualTo(storedNote.getId());
        assertThat(TestNote.NOTE_ID).isEqualTo(storedNote.getNoteId());
        assertThat(TestNote.HEADER).isEqualTo(storedNote.getHeader());
        assertThat(TestNote.BODY).isEqualTo(storedNote.getBody());
        assertThat(TestNote.COMMENT).isEqualTo(storedNote.getComment());
        log.info("Done Testing NoteRepository Store Note");
    }

    /**
     * Saves a test NoteDocument to the repository and then retrieves that
     * NoteDocument and asserts the values from the stored NoteDocument.
     */
    @Test
    public void testRepositoryStoreNoteDocument() {
        NoteDocument noteDocument = null;
        NoteDocument storedNoteDoc = null;
        Long noteId = Long.valueOf(5656);
        String docId = "HSA897";

        log.info("Testing NoteDocumenRepository Storing");
        noteDocument = new NoteDocument(noteId, docId);
        noteDocRepository.saveAndFlush(noteDocument);
        storedNoteDoc = noteDocRepository.findNoteDocumentByNoteIdAndDocId(noteId, docId).get();
        assertThat(noteId).isEqualTo(storedNoteDoc.getNoteId());
        assertThat(docId).isEqualTo(storedNoteDoc.getDocId());
        log.info("Done Testing NoteDocumenRepository Storing");
    }
}