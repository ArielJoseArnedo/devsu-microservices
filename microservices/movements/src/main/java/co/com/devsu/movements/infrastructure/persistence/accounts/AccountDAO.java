package co.com.devsu.movements.infrastructure.persistence.accounts;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface AccountDAO extends CrudRepository<AccountRecord, AccountKey> {
    Optional<AccountRecord> findByAccountKey_NumberAccount(String numberAccount);
    List<AccountRecord> findByAccountKey_ClientId(String clientId);
}
