package vending.exception;

public class InvalidChoiceException extends RuntimeException{

    public InvalidChoiceException() {
    }

    public InvalidChoiceException(String message) {
        super(message);
    }

    public InvalidChoiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidChoiceException(Throwable cause) {
        super(cause);
    }


}
