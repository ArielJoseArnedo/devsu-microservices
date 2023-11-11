package co.com.devsu.clients.infrastructure.events;

public interface Event {
    String getName();
    <T> T getMessage();
}
