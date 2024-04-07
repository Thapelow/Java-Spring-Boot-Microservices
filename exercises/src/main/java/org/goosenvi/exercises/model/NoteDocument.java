package org.goosenvi.exercises.model;

import jakarta.persistence.*;

@Entity
@Table(name = "NOTE_DOCUMENT")
public class NoteDocument {

    /**
     * The primary key identifier with a sequence defined in the database.
     */
    @SequenceGenerator(name = "note_doc_id", sequenceName = "public.note_doc_id_seq_pg", allocationSize = 3)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "note_doc_id")
    @Id
    @Column(name = "PK_ID")
    private Long id = null;

    /**
     * The <code>Note</code> identifier.
     */
    @Column(name = "NOTE_ID")
    private Long noteId = null;

    /**
     * The <code>Document</code> identifier.
     */
    @Column(name = "DOC_ID")
    private String docId = "";

    /**
     * The default constructor.
     */
    public NoteDocument() {
        super();
    }

    /**
     * The constructor using all fields.
     *
     * @param id     the database generated identifier.
     * @param noteId the Note identifier, must be unique
     * @param docId  the Document identifier
     */
    public NoteDocument(Long id, Long noteId, String docId) {
        super();
        this.id = id;
        this.noteId = noteId;
        this.docId = docId;
    }

    /**
     * The constructor using the noteId and docId fields.
     *
     * @param noteId the Note identifier, must be unique
     * @param docId  the Document identifier
     */
    public NoteDocument(Long noteId, String docId) {
        super();
        this.noteId = noteId;
        this.docId = docId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}