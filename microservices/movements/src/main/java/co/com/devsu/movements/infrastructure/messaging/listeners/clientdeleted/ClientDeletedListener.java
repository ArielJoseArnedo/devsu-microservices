package co.com.devsu.movements.infrastructure.messaging.listeners.clientdeleted;

import co.com.devsu.movements.domain.models.Account;
import co.com.devsu.movements.domain.services.AccountService;
import co.com.devsu.movements.infrastructure.events.AccountDeleted;
import co.com.devsu.movements.infrastructure.events.Event;
import co.com.devsu.movements.infrastructure.messaging.listeners.Listener;
import co.com.devsu.movements.infrastructure.messaging.listeners.clientregisted.ClientRegistedMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ClientDeletedListener implements Listener {

    private final AccountService accountService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ClientDeletedListener(AccountService accountService, ObjectMapper objectMapper) {
        this.accountService = accountService;
        this.objectMapper = objectMapper;
    }

    @Override
    public String getName() {
        return "ClientDeleted";
    }

    @Override
    public Flux<Event> process(JsonNode message) {
        log.info("Event processing starts: {} with data: {}", getName(), message);
        return transform(objectMapper, message, ClientRegistedMessage.class)
          .flatMapMany(clientRegistedMessage -> accountService.getAccountsByClient(clientRegistedMessage.getClientId()))
          .flatMap(accountService::deleteAccount)
          .map(AccountDeleted::new);
    }
}
