package org.goosenvi.exercises.storage;

import org.goosenvi.exercises.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {
    Note findNoteByNoteId(Long noteId);
    Note[] findNotesByHeader(String header);
}
