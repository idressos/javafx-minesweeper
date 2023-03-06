package gr.ntua.ece.medialab.minesweeper.exceptions;

public class InvalidDescriptionException extends Exception {
    
    public InvalidDescriptionException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}