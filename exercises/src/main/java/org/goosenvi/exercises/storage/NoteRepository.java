package org.goosenvi.exercises.storage;

import org.goosenvi.exercises.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    /**
     * Find a Note using a note identifier.
     *
     * @param noteId the note identifier
     *
     * @return an Optional Note that can contain an empty result
     */
    Optional<Note> findNoteByNoteId(Long noteId);

    /**
     * Find Notes using a specified header value.
     *
     * @param header the header to search for
     *
     * @return a list of all Notes with the specified header
     */
    Note[] findNotesByHeader(String header);

}
