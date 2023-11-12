package co.com.devsu.movements.infrastructure.persistence;

import co.com.devsu.movements.domain.models.Movement;
import co.com.devsu.movements.domain.ports.MovementRepository;
import co.com.devsu.movements.infrastructure.persistence.movements.MovementDAO;
import co.com.devsu.movements.infrastructure.persistence.movements.MovementMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class MovementJpaRepository implements MovementRepository, MovementMapper {

    private final MovementDAO movementDAO;

    @Autowired
    public MovementJpaRepository(MovementDAO movementDAO) {
        this.movementDAO = movementDAO;
    }

    @Override
    public Mono<Movement> registerMovement(Movement movement, String clientId, String numberAccount) {
        return Mono.just(toRecord(movement, clientId, numberAccount))
          .map(movementDAO::save)
          .map(this::toDomain);
    }

    @Override
    public Flux<Movement> findAllMovementByNumberAccount(String clientId, String numberAccount) {
        return null;
    }
}
