package org.goosenvi.exercises.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Exercise1Controller {
    @Autowired
    protected Environment environment = null;
    private static final Logger log = LoggerFactory.getLogger(Exercise1Controller.class);

    @GetMapping("exercise1")
    public void printToConsole(){
        log.info("Exerxise1, GetMapping received.");
    }

    @GetMapping("exercise1/{usernumber}")
    public void printUserNumber(@PathVariable Long usernumber){
        log.info("User number received: " + usernumber);
        usernumber += 333;
        log.debug("User numer after addding: " + usernumber);
    }

    @GetMapping("exercise1/config")
    public void configValueToConsole(){
        String configValue = "";
        configValue = environment.getProperty("org.goosenvi.exercise1");
        log.info("Property Value of org.goosenvi.exercise1 is: " + configValue);
    }
}
