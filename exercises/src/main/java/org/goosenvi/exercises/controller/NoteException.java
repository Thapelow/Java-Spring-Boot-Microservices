package org.goosenvi.exercises.controller;

public class NoteException extends Exception {

    /**
     * A constructor with a message and the causing exception.
     *
     * @param message the error message
     * @param cause   the root exception
     */
    public NoteException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * A constructor with a message.
     *
     * @param message the error message
     */
    public NoteException(String message) {
        super(message);
    }

}