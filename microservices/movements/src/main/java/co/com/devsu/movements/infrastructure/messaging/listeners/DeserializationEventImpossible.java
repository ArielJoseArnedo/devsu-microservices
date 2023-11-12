package co.com.devsu.movements.infrastructure.messaging.listeners;

public class DeserializationEventImpossible extends Exception {

    public DeserializationEventImpossible(String message) {
        super(message);
    }
}
