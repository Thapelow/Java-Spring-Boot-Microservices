package org.goosenvi.exercises.model;

public class NotePlain {
    /**
     * The note id
     */
    private Long noteId;
    /**
     * The header of the note
     */
    private String header = "";
    /**
     * The body of the note
     */
    private String body = "";
    /**
     * A comment about the note
     */
    private String comment = "";

    /**
     * Default constructor
     */
    public NotePlain(){
        super();
    }
    /**
     * The constructor using all fields
     * @param noteId
     * @param header
     * @param body
     * @param comment
     */
    public NotePlain(Long noteId,String header,String body,String comment){
        this.body =body;
        this.comment = comment;
        this.noteId = noteId;
        this.header = header;
    }

    public Long getNoteId() {
        return noteId;
    }

    public String getBody() {
        return body;
    }

    public String getComment() {
        return comment;
    }

    public String getHeader() {
        return header;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public String getNoteInfo(){
        return "Note Id: " + noteId + " Header: " + header + " Body: " + body + " Comment" + comment;
    }
}
