package org.goosenvi.exercises.model;

import org.goosenvi.exercises.model.document.Document;

import java.util.ArrayList;

public class NoteDocumentBinder {

    /**
     * The actual Note that is referenced by the noteId.
     */
    private Note note = null;

    /**
     * The list of Document identifiers.
     */
    private ArrayList<Document> docs = new ArrayList<Document>();


    /**
     * The default constructor.
     */
    public NoteDocumentBinder() {
        super();
    }

    /**
     * The constructor using all the fields.
     *
     * @param note		the actual Note with all Note information
     * @param docIds	the Document identifiers
     *
     */
    public NoteDocumentBinder(Note note, ArrayList<Document> docs) {
        super();
        this.note = note;
        this.docs = docs;
    }

    /**
     * Adds a Document to the list of documents that the Note references.
     *
     * @param document the Document to add
     */
    public void addDocument(Document document) {
        this.docs.add(document);
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public ArrayList<Document> getDocs() {
        return this.docs;
    }

    public void setDocs(ArrayList<Document> docs) {
        this.docs = docs;
    }
}