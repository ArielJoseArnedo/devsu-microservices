package co.com.devsu.clients.infrastructure.messaging;

import co.com.devsu.clients.infrastructure.events.Event;
import co.com.devsu.clients.infrastructure.messaging.listeners.Listener;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@Component
public class Suscriber implements SuscriberUtil {

    private final List<Listener> listeners;
    private final ObjectMapper objectMapper;

    @Autowired
    public Suscriber(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.listeners = List.empty();
    }

    public void onMessage(Object message) {
        log.info("Event received: {}", message.toString());
        getDataEvent(message)
          .map(eventNameAndEventMessage -> eventNameAndEventMessage
            .map1(eventName -> listeners.find(listener -> listener.getName().equalsIgnoreCase(eventName)))
          ).map(this::executeListener)
          .getOrElse(Flux.empty())
          .subscribe();
    }

    public Flux<Event> executeListener(Tuple2<Option<Listener>, JsonNode> listenerAndEventMessage) {
        return listenerAndEventMessage.apply((listenerOpt, messageJson) ->
          listenerOpt
            .map(listener -> listener.process(messageJson))
            .getOrElse(Flux.empty())
        );
    }

    private Try<Tuple2<String, JsonNode>> getDataEvent(Object message) {
        return getJsonNode(message, objectMapper)
          .flatMap(jsonNode -> getEventName(jsonNode, "name")
            .flatMap(eventName -> getMessage(jsonNode, "message")
              .map(eventMessage -> Tuple.of(eventName, eventMessage)))
          );
    }
}
