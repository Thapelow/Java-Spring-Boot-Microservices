package org.goosenvi.exercises.model;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;

import org.goosenvi.exercises.controller.Exercise5Controller;

public class NoteJsonParser {

    /**
     * The Simple Logging Facade for Java. Spring uses Logback framework.
     */
    private static final Logger log = LoggerFactory.getLogger(Exercise5Controller.class);

    /**
     * The list of notes.
     */
    private ArrayList<Note> notes = new ArrayList<Note>();

    /**
     * Returns the text value if the token is a VALUE token.
     *
     * @param parser the JsonParser with the token position at a field
     *
     * @return the text value from the Value token
     *
     * @throws IOException         if there is an exception in the Jackson parser
     * @throws NoteParserException if the token does not start with 'VALUE'
     */
    private String getFieldValue(JsonParser parser) throws IOException, NoteParserException {
        String tokenAsString = "";

        tokenAsString = parser.nextToken().name();
        if (tokenAsString.startsWith("VALUE")) {
            return parser.getText();
        } else {
            throw new NoteParserException("Expected VALUE token to start with 'VALUE'");
        }
    }

    /**
     * Parses the JSON Note object and add a note to the list of notes.
     *
     * @param parser the JsonParser with the token position at the beginning of the
     *               Note object
     *
     * @throws IOException         if there is an exception in the Jackson parser
     * @throws NoteParserException if there is an error getting the field value
     */
    private void parseNoteObject(JsonParser parser) throws IOException, NoteParserException {
        String tokenAsString = "";
        String noteId = "";
        String header = "";
        String body = "";
        String comment = "";
        JsonToken token = null;
        String text = "";

        try {
            log.info("Parsing Note object");
            while (parser.nextToken() != null) {
                token = parser.getCurrentToken();
                // log.debug("Token: " + parser.getCurrentToken());
                // log.debug("Name: " + parser.getCurrentName());
                log.debug(parser.getText());

                tokenAsString = token.name();
                log.debug("Token as String: " + tokenAsString);
                if (tokenAsString.equals("FIELD_NAME")) {
                    text = parser.getText();
                    if (text.equals("noteId")) {
                        noteId = getFieldValue(parser);
                    } else if (text.equals("header")) {
                        header = getFieldValue(parser);
                    } else if (text.equals("body")) {
                        body = getFieldValue(parser);
                    } else if (text.equals("comment")) {
                        comment = getFieldValue(parser);
                    }
                } else if (tokenAsString.equals("END_OBJECT")) {
                    break;
                }
            }

            log.info("noteId: " + noteId);
            log.info("header: " + header);
            log.info("body: " + body);
            log.info("comment: " + comment);
            log.info("Finished parsing Note object with note id: " + noteId);
            notes.add(new Note(Long.valueOf(-1), Long.valueOf(noteId), header, body, comment));
        } catch (Exception e) {
            log.error("Error parsing the JSON Note Object", e);
            throw e;
        }
    }

    /**
     * Parses a Note array.
     *
     * @param parser the JsonParser with the token position at the beginning of the
     *               Note array
     *
     * @throws IOException         if there is an exception in the Jackson parser
     * @throws NoteParserException if there is an error getting the field value
     */
    private void parseNoteArray(JsonParser parser) throws IOException, NoteParserException {
        JsonToken token = null;

        try {
            log.info("Parsing Note array");
            while (parser.nextToken() != null) {
                token = parser.getCurrentToken();
                // log.debug("Token: " + parser.getCurrentToken());
                // log.debug("Name: " + parser.getCurrentName());
                log.debug(parser.getText());

                if (token.equals(JsonToken.START_OBJECT)) {
                    parseNoteObject(parser);
                } else if (token.equals(JsonToken.END_ARRAY)) {
                    break;
                }
            }
            log.info("Finished parsing Note array");
        } catch (Exception e) {
            log.error("Error parsing the JSON Note Object", e);
            throw e;
        }
    }

    /**
     * Parse the JSON Array or Object and return a list of Notes.
     *
     * @param payload the JsonNode that begins the JSON tree.
     *
     * @return a list of Notes
     *
     * @throws IOException         if there is an error in the Jackson parser
     * @throws NoteParserException if there is any unexpected values in the JSON
     *                             array
     */
    public ArrayList<Note> parserJson(JsonNode payload) throws IOException, NoteParserException {
        JsonParser parser = null;
        JsonToken token = null;

        try {
            parser = JsonFactory.builder().build().createParser(payload.toPrettyString().getBytes());
            token = parser.getCurrentToken();
            while (parser.nextToken() != null) {
                token = parser.getCurrentToken();
                // log.debug("Token: " + parser.getCurrentToken());
                // log.debug("Name: " + parser.getCurrentName());
                log.info(parser.getText());
                if (token.equals(JsonToken.START_ARRAY)) {
                    parseNoteArray(parser);
                    break;
                } else if (token.equals(JsonToken.START_OBJECT)) {
                    parseNoteObject(parser);
                    break;
                }
            }
        } catch (Exception e) {
            log.error("Error parsing the JSON Note Object", e);
            throw e;
        }

        return notes;
    }
}
