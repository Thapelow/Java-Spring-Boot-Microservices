package org.goosenvi.exercises.model;

import java.util.ArrayList;

public class NoteDocumentRefBinder {

    /**
     * The Note.
     */
    private Note note = null;

    /**
     * The list of Document identifiers referenced by the Note.
     */
    private ArrayList<String> docIds = new ArrayList<String>();

    /**
     * The default constructor.
     */
    public NoteDocumentRefBinder() {
        super();
    }

    /**
     * The constructor using the Note and Documents id fields.
     *
     * @param note   the Note
     * @param docIds the Document identifiers
     */
    public NoteDocumentRefBinder(Note note, ArrayList<String> docIds) {
        super();
        this.note = note;
        this.docIds = docIds;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public ArrayList<String> getDocIds() {
        return docIds;
    }

    public void setDocIds(ArrayList<String> docIds) {
        this.docIds = docIds;
    }

    /**
     * Adds the document reference (docId) to the list of documents that the Note
     * references.
     *
     * @param docId the document id to add to the list
     */
    public void addDocumentReference(String docId) {
        docIds.add(docId);
    }
}