package co.com.devsu.movements.domain.services;

import co.com.devsu.movements.domain.errors.AccountNotFound;
import co.com.devsu.movements.domain.errors.BalanceNotAvailable;
import co.com.devsu.movements.domain.errors.MovementTypeNotSupported;
import co.com.devsu.movements.domain.models.Account;
import co.com.devsu.movements.domain.models.Movement;
import co.com.devsu.movements.domain.ports.AccountRepository;
import co.com.devsu.movements.domain.ports.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

@Service
public class MovementService {

    private static final String WITHDRAWAL = "withdrawal";
    private static final String DEPOSIT = "deposit";

    private final AccountRepository accountRepository;
    private final MovementRepository movementRepository;

    @Autowired
    public MovementService(AccountRepository accountRepository, MovementRepository movementRepository) {
        this.accountRepository = accountRepository;
        this.movementRepository = movementRepository;
    }

    public Mono<Movement> registerMovement(Movement movement, String clientId, String numberAccount) {
        return accountRepository.findByClientAndNumberAccount(clientId, numberAccount)
          .flatMap(accountOpt -> accountOpt
            .map(account -> verifyBalance(account, movement)
              .flatMap(movementVerified -> updateBalanceAndRegisterMovement(account, movementVerified))
            )
            .getOrElse(Mono.error(new AccountNotFound("The account does not exist")))
          );
    }

    public Mono<Movement> verifyBalance(Account account, Movement movement) {
        return Mono.create(sink -> {
              if(movement.getMovementType().equalsIgnoreCase(WITHDRAWAL)) {
                  if(account.getBalance() - movement.getAmount() >= 0.0) {
                      sink.success(movement);
                  } else {
                      sink.error(new BalanceNotAvailable("Balance not available"));
                  }
              } else {
                  sink.success(movement);
              }
        });
    }

    public Mono<Movement> updateBalanceAndRegisterMovement(Account accountVerified, Movement movement) {
        return Match(movement.getMovementType()).of(
            Case($(is(WITHDRAWAL)), () -> Mono.just(accountVerified.getBalance() - movement.getAmount())),
            Case($(is(DEPOSIT)), () -> Mono.just(accountVerified.getBalance() + movement.getAmount())),
            Case($(), () -> Mono.<Double>error(new MovementTypeNotSupported("Type of movement not allowed")))
          ).map(accountVerified::updateBalance)
          .flatMap(accountRepository::update)
          .flatMap(accountUpdated -> movementRepository.registerMovement(movement, accountUpdated.getClientId(), accountUpdated.getNumberAccount()));
    }

    private static Predicate<String> is(String value) {
        return obj -> obj.equalsIgnoreCase(value);
    }
}
