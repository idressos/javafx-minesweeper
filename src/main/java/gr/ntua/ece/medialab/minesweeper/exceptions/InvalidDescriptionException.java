package gr.ntua.ece.medialab.minesweeper.exceptions;

public class InvalidDescriptionException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    public InvalidDescriptionException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}