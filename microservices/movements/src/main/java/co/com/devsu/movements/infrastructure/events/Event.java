package co.com.devsu.movements.infrastructure.events;

public interface Event {
    String getName();
    <T> T getMessage();
}
