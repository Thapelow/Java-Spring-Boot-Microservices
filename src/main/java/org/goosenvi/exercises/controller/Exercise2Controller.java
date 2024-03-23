package org.goosenvi.exercises.controller;

import org.goosenvi.exercises.model.NotePlain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Exercise2Controller {
    private static Logger log = LoggerFactory.getLogger(Exercise2Controller.class);

    @PostMapping("exercise2/note")
    public void logPOSTnote(@RequestBody NotePlain note){
        log.info("Exercise 2, Post mapping received. Note information: " + note);
    }
}
