package co.com.devsu.movements.infrastructure.persistence.accounts;

import co.com.devsu.movements.domain.models.Account;
import co.com.devsu.movements.infrastructure.persistence.movements.MovementMapper;
import co.com.devsu.movements.infrastructure.persistence.movements.MovementRecord;
import io.vavr.collection.List;


public interface AccountMapper extends MovementMapper {

    default Account toDomain(AccountRecord accountRecord) {
        return Account.builder()
          .clientId(accountRecord.getAccountKey().getClientId())
          .numberAccount(accountRecord.getAccountKey().getNumberAccount())
          .accountType(accountRecord.getAccountType())
          .balance(accountRecord.getBalance())
          .state(accountRecord.getState())
          .movements(List.ofAll(accountRecord.getMovementRecords()).map(this::toDomain))
          .build();
    }

    default AccountRecord toRecord(Account account) {
        return AccountRecord.builder()
          .accountKey(new AccountKey(account.getClientId(), account.getNumberAccount()))
          .accountType(account.getAccountType())
          .balance(account.getBalance())
          .state(account.getState())
          .movementRecords(List.<MovementRecord>empty().toJavaList())
          .build();
    }
}
