package org.goosenvi.exercises.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.goosenvi.exercises.model.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Exercise5Controller {
    private static Logger log = LoggerFactory.getLogger(Exercise5Controller.class);

    @PostMapping("exercise5/string")
    public void writeStringNoteToConsole(@RequestBody String payload){
        log.info("Exercise 5, Post mapping received. " + payload);
    }
    @PostMapping("exercise5/serialized")
    public void writeNoteSerializedToConsole(@RequestBody Note note){
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String noteString = "";
        try {
            noteString = mapper.writeValueAsString(note);
            log.info("Exercise 5, Post mapping received serialized value: " + noteString);
        }catch (Exception e){
            log.error("Error occurred when transforming Note with mapper: ", e);
        }
    }
    @PostMapping("exercise5/deserialize")
    public void deserializedStringNoteToConsole(@RequestBody String payload){
        ObjectMapper mapper = new ObjectMapper();
        Note deserializedNote = null;
        log.info("Exercise 5 Post Mapping received, String payload: " + payload);
        try {
            deserializedNote = mapper.readValue(payload,Note.class);
            log.info("Note value after: " + deserializedNote.noteInformation());
        }catch (Exception e){
            log.error("Error occurred when transforming Note with mapper: ", e);
        }
    }
}
