package co.com.devsu.movements.domain.errors;

public class AccountNotFound extends Exception {
    public AccountNotFound(String message) {
        super(message);
    }
}
