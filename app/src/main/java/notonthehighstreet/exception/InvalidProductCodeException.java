package notonthehighstreet.exception;

public class InvalidProductCodeException extends RuntimeException {

    public InvalidProductCodeException() {
        super("One or more products have invalid codes!");
    }
}