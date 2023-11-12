package co.com.devsu.movements.domain.errors;

public class BalanceNotAvailable extends Exception {
    public BalanceNotAvailable(String message) {
        super(message);
    }
}
