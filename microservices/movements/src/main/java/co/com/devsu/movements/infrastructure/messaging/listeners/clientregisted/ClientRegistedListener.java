package co.com.devsu.movements.infrastructure.messaging.listeners.clientregisted;

import co.com.devsu.movements.infrastructure.events.Event;
import co.com.devsu.movements.infrastructure.messaging.listeners.Listener;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@Component
public class ClientRegistedListener implements Listener {

    @Override
    public String getName() {
        return "ClientRegisted";
    }

    @Override
    public Flux<Event> process(JsonNode message) {
        log.info("Event processing starts: {} with data: {}", getName(), message);
        return Flux.empty();
    }
}
