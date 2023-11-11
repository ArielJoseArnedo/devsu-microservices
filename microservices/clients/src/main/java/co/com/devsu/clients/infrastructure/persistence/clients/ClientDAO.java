package co.com.devsu.clients.infrastructure.persistence.clients;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface ClientDAO extends CrudRepository<ClientRecord, String> {
    Optional<ClientRecord> findByPersonRecord_Identification(String identification);
}
