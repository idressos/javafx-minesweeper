package gr.ntua.ece.medialab.minesweeper.exceptions;

public class InvalidValueException extends Exception {
    
    public InvalidValueException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}