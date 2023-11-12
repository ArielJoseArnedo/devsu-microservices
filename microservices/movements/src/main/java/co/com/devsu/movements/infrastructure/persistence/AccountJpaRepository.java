package co.com.devsu.movements.infrastructure.persistence;

import co.com.devsu.movements.domain.models.Account;
import co.com.devsu.movements.domain.ports.AccountRepository;
import co.com.devsu.movements.infrastructure.persistence.accounts.AccountDAO;
import co.com.devsu.movements.infrastructure.persistence.accounts.AccountKey;
import co.com.devsu.movements.infrastructure.persistence.accounts.AccountMapper;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Repository
public class AccountJpaRepository implements AccountRepository, AccountMapper {

    private final AccountDAO accountDAO;

    @Autowired
    public AccountJpaRepository(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public Mono<Account> registerAccount(Account account) {
        return Mono.just(toRecord(account))
          .map(accountDAO::save)
          .map(accountRecord -> account);
    }

    @Override
    public Mono<Option<Account>> findByClientAndNumberAccount(String clientId, String numberAccount) {
        return Mono.just(Option.ofOptional(accountDAO.findById(new AccountKey(clientId, numberAccount)))
          .map(this::toDomain)
          );
    }

    @Override
    public Mono<Option<Account>> findByNumberAccount(String numberAccount) {
        return Mono.just(Option.ofOptional(accountDAO.findByAccountKey_NumberAccount(numberAccount))
          .map(this::toDomain)
        );
    }

    @Override
    public Flux<Account> findByClient(String clientId) {
        return Flux.fromIterable(accountDAO.findByAccountKey_ClientId(clientId))
          .map(this::toDomain);
    }

    @Override
    public Mono<Account> update(Account account) {
        return Mono.just(toRecord(account))
          .map(accountDAO::save)
          .map(accountRecord -> account);
    }

    @Override
    public Mono<Account> delete(Account account) {
        return Mono.just(toRecord(account))
          .publishOn(Schedulers.boundedElastic())
          .map(accountRecord -> {
              accountDAO.delete(accountRecord);
              return account;
          });
    }
}
