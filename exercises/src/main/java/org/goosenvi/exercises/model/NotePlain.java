package org.goosenvi.exercises.model;

public class NotePlain {

    /**
     * The Note identifier.
     */
    private Long noteId = null;

    /**
     * The Note header.
     */
    private String header = "";

    /**
     * The Note body content.
     */
    private String body = "";

    /**
     * A comment about the Note.
     */
    private String comment = "";

    /**
     * The default constructor.
     */
    public NotePlain() {
        super();
    }

    /**
     * The NotePlain constructor using all the fields.
     *
     * @param id      the Note id
     * @param header  the Note header
     * @param body    the Note body content
     * @param comment a comment about the Note
     */
    public NotePlain(Long noteId, String header, String body, String comment) {
        super();
        this.noteId = noteId;
        this.header = header;
        this.body = body;
        this.comment = comment;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getNoteInformation() {
        return "NoteId: " + getNoteId() + ", Header: " + getHeader() + ", Body: " + getBody() + ", Comment: " + getComment();
    }
}