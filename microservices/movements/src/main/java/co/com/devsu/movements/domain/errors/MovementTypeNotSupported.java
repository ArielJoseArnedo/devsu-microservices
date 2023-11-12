package co.com.devsu.movements.domain.errors;

public class MovementTypeNotSupported extends Exception {
    public MovementTypeNotSupported(String message) {
        super(message);
    }
}
