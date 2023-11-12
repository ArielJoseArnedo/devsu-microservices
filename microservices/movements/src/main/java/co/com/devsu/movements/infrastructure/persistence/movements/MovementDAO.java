package co.com.devsu.movements.infrastructure.persistence.movements;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface MovementDAO extends CrudRepository<MovementRecord, MovementKey> {
}
