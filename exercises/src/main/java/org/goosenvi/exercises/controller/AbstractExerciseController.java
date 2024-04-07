package org.goosenvi.exercises.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.annotation.PostConstruct;

import org.goosenvi.exercises.MicroClient;

@RestController
public abstract class AbstractExerciseController {

    protected String documentUrl = "";

    protected String documentPath = "";

    /**
     * The Spring core environment.
     */
    @Autowired
    protected Environment environment = null;

    /**
     * The client to call other Microservices.
     */
    protected MicroClient microClient = null;

    /**
     * The default constructor.
     */
    public AbstractExerciseController() {
        super();
    }

    /**
     * The exception handler form exercise 3.
     *
     * Spring's general procedure to send response exceptions to calling
     * applications. Reads the message and status from the application level
     * exception.
     *
     * @param rse the ResponseStatusException that contains status code, message and
     *            exception.
     * @return a ResponseEntity with HTTP status code, body and header.
     */
    @ExceptionHandler
    public ResponseEntity<String> handle(ResponseStatusException rse) {
        return new ResponseEntity<String>(rse.getMessage(), rse.getStatusCode());
    }

    /**
     * Set the document URL and PATH from the configuration file.
     */
    @PostConstruct
    private void init() {
        this.documentUrl = environment.getProperty("se.udemy.document.service.url");
        this.documentPath = environment.getProperty("se.udemy.document.service.path");
    }

}
