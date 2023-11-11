package co.com.devsu.clients.infrastructure.persistence;

import co.com.devsu.clients.domain.models.Client;
import co.com.devsu.clients.domain.ports.ClientRepository;
import co.com.devsu.clients.infrastructure.persistence.clients.ClientDAO;
import co.com.devsu.clients.infrastructure.persistence.clients.ClientMapper;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ClientJpaRepository implements ClientRepository {

    private final ClientDAO clientDAO;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientJpaRepository(ClientDAO clientDAO, ClientMapper clientMapper) {
        this.clientDAO = clientDAO;
        this.clientMapper = clientMapper;
    }

    @Override
    public Mono<Client> save(Client client) {
        return Mono.just(clientMapper.toRecord(client))
          .map(clientDAO::save)
          .map(clientRecord -> client);
    }

    @Override
    public Mono<Option<Client>> findById(String clientId) {
        return Mono.just(Option.ofOptional(clientDAO.findById(clientId)))
          .map(clientRecordOption -> clientRecordOption
            .map(clientMapper::toDomain)
          );
    }

    @Override
    public Mono<Option<Client>> findByIdentification(String identification) {
        return Mono.just(Option.ofOptional(clientDAO.findByPersonRecord_Identification(identification)))
          .map(clientRecordOption -> clientRecordOption
            .map(clientMapper::toDomain)
          );
    }

    @Override
    public Mono<Client> delete(Client client) {
        return Mono.just(clientMapper.toRecord(client))
          .map(clientRecord -> {
              clientDAO.delete(clientRecord);
              return client;
          });
    }

    @Override
    public Flux<Client> findAll() {
        return Flux.fromIterable(clientDAO.findAll())
          .map(clientMapper::toDomain);
    }

    @Override
    public Mono<Client> update(Client client) {
        return Mono.just(clientMapper.toRecord(client))
          .map(clientDAO::save)
          .map(clientMapper::toDomain);
    }
}
