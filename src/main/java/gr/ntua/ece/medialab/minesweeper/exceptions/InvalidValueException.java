package gr.ntua.ece.medialab.minesweeper.exceptions;

public class InvalidValueException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    public InvalidValueException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}