package co.com.devsu.movements.domain.services;

import co.com.devsu.movements.domain.errors.AccountNotFound;
import co.com.devsu.movements.domain.models.Account;
import co.com.devsu.movements.domain.ports.AccountRepository;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Mono<Account> createAccount(Account account) {
        log.info("The following account will be created: {}", account.toString());
        return accountRepository.findByClientAndNumberAccount(account.getClientId(), account.getNumberAccount())
          .flatMap(accountOpt -> accountOpt
            .map(accountAlreadyRegisted -> accountAlreadyRegisted.update(account))
            .map(accountRepository::update)
            .getOrElse(accountRepository.registerAccount(account))
          );
    }

    public Mono<Account> updateAccount(Account account) {
        return accountRepository.findByClientAndNumberAccount(account.getClientId(), account.getNumberAccount())
          .flatMap(accountOpt -> accountOpt
            .map(accountAlreadyRegisted -> accountAlreadyRegisted.update(account))
            .map(accountRepository::update)
            .getOrElse(Mono.error(new AccountNotFound("The account does not exist")))
          );
    }

    public Flux<Account> getAccountsByClient(String clientId) {
        log.info("The accounts of the following customer will be searched: {}", clientId);
        return accountRepository.findByClient(clientId);
    }

    public Flux<Account> getOneOrAccounts(Option<String> clientIdOpt) {
        log.info("One or all accounts will be searched according to: {}", clientIdOpt);
        return clientIdOpt
          .map(accountRepository::findByClient)
          .getOrElse(accountRepository::findAll);
    }

    public Mono<Option<Account>> getAccountByClient(String clientId, String numberAccount) {
        return accountRepository.findByClientAndNumberAccount(clientId, numberAccount);
    }

    public Mono<Account> deleteAccount(String clientId, String numberAccount) {
        return accountRepository.findByClientAndNumberAccount(clientId, numberAccount)
          .flatMap(accountOpt -> accountOpt
            .map(accountRepository::delete)
            .getOrElse(Mono.error(new AccountNotFound("The account does not exist")))
          );
    }

    public Mono<Account> deleteAccount(Account account) {
        return accountRepository.delete(account);
    }
}
