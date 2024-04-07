package org.goosenvi.exercises.controller;

import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.goosenvi.exercises.model.Note;
import org.goosenvi.exercises.model.NoteJsonParser;
@RestController
public class Exercise5Controller {

    /**
     * The Simple Logging Facade for Java. Spring uses Logback framework.
     */
    private static final Logger log = LoggerFactory.getLogger(Exercise5Controller.class);

    /**
     * Convert all notes to a CSV format, print them to standard out and then return
     * them in a String format.
     *
     * @param notes the list of notes to convert to CSV format
     *
     * @return a String with all the notes in a CSV format
     */
    private String printCSVformat(ArrayList<Note> notes) {
        Note note = null;
        Iterator<Note> iterator = null;
        String separator = ";";
        String result = "";

        try {
            iterator = notes.iterator();
            while (iterator.hasNext()) {
                note = (Note) iterator.next();
                result += "" + note.getNoteId() + separator + note.getHeader() + separator + note.getBody() + separator
                        + note.getComment() + System.lineSeparator();
            }
        } catch (Exception e) {
            log.error("Error creating the CSV format", e);
        }

        System.out.print(result);
        return result;
    }

    /**
     * POST method that log the raw String received.
     *
     * @param payload the String that contains all information from the HTTP body
     */
    @PostMapping("/exercise5/note/string")
    public void writeToConsole(@RequestBody String payload) {
        log.info("Exercise 5, get mapping received, raw payload: " + payload);
    }

    /**
     * POST method that parses the JSON array or object using Spring's Fasterxml
     * Jackson parser.
     *
     * @param payload the JsonNode (received by Spring as a JSON Note Array or
     *                object in the HTTP body)
     */
    @PostMapping("/exercise5/note/jsonnode")
    public String writeToConsole(@RequestBody JsonNode payload) {
        ArrayList<Note> notes = null;

        try {
            log.info("Exercise 5, GET mapping received, JsonNode payload: " + payload);
            log.info("Exercise 5, start parsing JsonNode payload");
            notes = new NoteJsonParser().parserJson(payload);
            log.info("Exercise 5, finished parsing JsonNode payload");
            return printCSVformat(notes);
        } catch (Exception e) {
            log.error("Error parsing the Note array or creating the CSV format", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Error parsing. Message: " + e.getMessage());
        }
    }

    /**
     * POST method that serializes the Note object to a String and log the value.
     *
     * @param note the Note to serialize (received by Spring as a JSON Note object
     *             in the HTTP body)
     */
    @PostMapping("exercise5/serialize")
    public void writeNoteSerializedToConsole(@RequestBody Note note) {
        ObjectMapper mapper = new ObjectMapper();
        String noteString = "";

        try {
            log.info("Exercise 5, GET mapping received, Note object info: " + note.noteInformation());
            noteString = mapper.writeValueAsString(note);
            log.info("Exercise 5, serialized value: " + noteString);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            noteString = mapper.writeValueAsString(note);
            log.info("Exercise 5, indented serialized value: " + noteString);
        } catch (Exception e) {
            log.error("Exercise 5, an error occurred serializing the Note using ObjectMapper", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Error serializing the Note. Message: " + e.getMessage());
        }
    }

    /**
     * POST method that deserialize the String JSON object to a Java Note object and
     * then log the note information.
     *
     * @param payload the JSON object in a String value
     */
    @PostMapping("exercise5/note/deserialize")
    public void deserializeStringNoteToConsole(@RequestBody String payload) {
        ObjectMapper mapper = new ObjectMapper();
        Note deserializedNote = null;

        log.info("Exercise 5, GET mapping received, serialized payload: " + payload);
        try {
            deserializedNote = mapper.readValue(payload, Note.class);
            log.info("Deserialized Note information: " + deserializedNote.noteInformation());
        } catch (Exception e) {
            log.error("Exercise 5, an error occurred deserilizing the Note using ObjectMapper", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Error deserializing the Note. Message: " + e.getMessage());
        }
    }
}