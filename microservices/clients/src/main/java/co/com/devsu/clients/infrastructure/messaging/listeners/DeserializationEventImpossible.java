package co.com.devsu.clients.infrastructure.messaging.listeners;

public class DeserializationEventImpossible extends Exception {

    public DeserializationEventImpossible(String message) {
        super(message);
    }
}
