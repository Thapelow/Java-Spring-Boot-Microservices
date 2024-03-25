package org.goosenvi.exercises.model;

public class NoteProperties {
    private Float size = null;
    public NoteProperties(){
        super();
    }
    public NoteProperties(Float size){
        super();
        this.size = size;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }
}
