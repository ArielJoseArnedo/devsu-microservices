package co.com.devsu.clients.domain.ports;

import co.com.devsu.clients.domain.models.Client;
import io.vavr.control.Option;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientRepository {

    Mono<Client> save(Client client);
    Mono<Option<Client>> findById(String clientId);
    Mono<Option<Client>> findByIdentification(String identification);
    Mono<Client> delete(Client client);
    Flux<Client> findAll();
    Mono<Client> update(Client client);
}
