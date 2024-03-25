package org.goosenvi.exercises.controller;

import jakarta.annotation.PostConstruct;
import org.goosenvi.exercises.model.NoteProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Exercise3Controller {
    @Autowired
    protected Environment environment = null;
    private static Logger log = LoggerFactory.getLogger(Exercise3Controller.class);

    private NoteProperties noteProperties = null;

    @PostConstruct
    protected void init(){
        Float noteSize = null;
        try {
            noteSize = Float.valueOf(environment.getProperty("org.goosenvi.exercise3.note.size"));
            log.info("Exercise 3, Post Construct note size value: " + noteSize);
            this.noteProperties = new NoteProperties(noteSize);
        }catch (Exception e){
            log.error("Error initializing the Note Properties ",e);
        }
    }
    @GetMapping("exercise3/note/properties")
    public NoteProperties getNoteProperties(){
        log.info("Exercise 3, return note properties");
        return noteProperties;
    }
}
