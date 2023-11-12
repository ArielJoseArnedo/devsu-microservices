package co.com.devsu.movements.infrastructure.messaging.listeners.clientupdated;

import co.com.devsu.movements.domain.models.Account;
import co.com.devsu.movements.domain.services.AccountService;
import co.com.devsu.movements.infrastructure.events.AccountUpdated;
import co.com.devsu.movements.infrastructure.events.Event;
import co.com.devsu.movements.infrastructure.messaging.listeners.Listener;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@Component
public class ClientUpdatedListener implements Listener {

    private final AccountService accountService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ClientUpdatedListener(AccountService accountService, ObjectMapper objectMapper) {
        this.accountService = accountService;
        this.objectMapper = objectMapper;
    }

    @Override
    public String getName() {
        return "ClientUpdated";
    }

    @Override
    public Flux<Event> process(JsonNode message) {
        log.info("Event processing starts: {} with data: {}", getName(), message);
        return transform(objectMapper, message, ClientUpdatedMessage.class)
          .flatMapMany(clientUpdatedMessage -> accountService.getAccountsByClient(clientUpdatedMessage.getClientId())
            .map(account -> updateName(account, clientUpdatedMessage.getName()))
          ).flatMap(accountService::updateAccount)
          .map(AccountUpdated::new);
    }

    private Account updateName(Account account, String name) {
        return account.update(Account.builder()
          .clientName(name)
          .build()
        );
    }
}
