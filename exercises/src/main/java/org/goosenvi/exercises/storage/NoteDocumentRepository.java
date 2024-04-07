package org.goosenvi.exercises.storage;

import org.goosenvi.exercises.model.NoteDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoteDocumentRepository extends JpaRepository<NoteDocument, Long> {

    /**
     * Find all NoteDocuments that have the same note identifier.
     *
     * @param noteId the note identifier
     *
     * @return a list of all NoteDocuments that have the same noteId
     */
    NoteDocument[] findAllNoteDocumentsByNoteId(Long noteId);

    /**
     * Find a Note using the Note identifier and the Document identifier.
     *
     * @param noteId the note identifier
     * @param docId  the document identifier
     *
     * @return an Optional NoteDocument that can contain an empty result
     */
    Optional<NoteDocument> findNoteDocumentByNoteIdAndDocId(Long noteId, String docId);

}
