package co.com.devsu.clients.domain.services;

import co.com.devsu.clients.domain.errors.ClientAlreadyRegisted;
import co.com.devsu.clients.domain.errors.ClientNotFound;
import co.com.devsu.clients.domain.models.Client;
import co.com.devsu.clients.domain.ports.ClientRepository;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ClientService {

    private static final String CLIENT_WAS_NOT_FOUND = "Client was not found";
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Mono<Client> registerClient(Client client) {
        return clientRepository.findByIdentification(client.getIdentification())
          .flatMap(clientOption -> clientOption
            .map(clientAlreadyRegisted -> Mono.<Client>error(new ClientAlreadyRegisted("Client already registed")))
            .getOrElse(() -> clientRepository.save(client))
          );
    }

    public Mono<Client> updateClient(Client client) {
        return clientRepository.findById(client.getClientId())
          .flatMap(clientOption -> clientOption
            .map(clientToUpdate -> clientToUpdate.update(client))
            .map(clientRepository::update)
            .getOrElse(() -> Mono.error(new ClientNotFound(CLIENT_WAS_NOT_FOUND)))
          );
    }

    public Mono<Client> deleteClient(String clientId) {
        return clientRepository.findById(clientId)
          .flatMap(clientOption -> clientOption
            .map(clientRepository::delete)
            .getOrElse(() -> Mono.error(new ClientNotFound(CLIENT_WAS_NOT_FOUND)))
          );
    }

    public Flux<Client> getOneOrClients(Option<String> clientIdOpt) {
        log.info("One or all users will be searched according to: {}", clientIdOpt);
        return clientIdOpt
          .map(clientId -> clientRepository.findById(clientId)
            .flatMap(clientOpt -> clientOpt
              .map(Mono::just)
              .getOrElse(() -> Mono.error(new ClientNotFound(CLIENT_WAS_NOT_FOUND)))
            )
            .flux()
          ).getOrElse(clientRepository::findAll);
    }
}
