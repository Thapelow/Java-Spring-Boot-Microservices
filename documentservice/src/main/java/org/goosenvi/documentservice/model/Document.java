package org.goosenvi.documentservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "DOCUMENT")
public class Document {
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Id
    @Column(name = "PK_ID")
    private Long id = null;
    @Column(name = "DOC_ID", unique = true)
    private String docId = "";
    @Column(name = "NAME")
    private String name = "";
    @Column(name = "DESCRIPTION")
    private String description = "";
    @Column(name = "PATH")
    private String path = "";

    public Document(){
        super();
    }
    public Document(Long id, String docId, String name, String description, String path){
        super();
        this.id = id;
        this.docId = docId;
        this.name = name;
        this.description = description;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public String getDocId() {
        return docId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPath() {
        return path;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String documentInformation(){
        return "id: " + id + ", docId: " + docId + ", name: " + name + ", description: " + description + ", path: " + path;
    }
}
