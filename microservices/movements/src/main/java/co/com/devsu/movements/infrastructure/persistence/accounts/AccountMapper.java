package co.com.devsu.movements.infrastructure.persistence.accounts;

import co.com.devsu.movements.domain.models.Account;
import co.com.devsu.movements.domain.models.AccountState;
import co.com.devsu.movements.domain.models.AccountType;
import co.com.devsu.movements.infrastructure.persistence.movements.MovementMapper;
import co.com.devsu.movements.infrastructure.persistence.movements.MovementRecord;
import io.vavr.collection.List;


public interface AccountMapper extends MovementMapper {

    default Account toDomain(AccountRecord accountRecord) {
        return Account.builder()
          .clientId(accountRecord.getAccountKey().getClientId())
          .clientName(accountRecord.getClientName())
          .numberAccount(accountRecord.getAccountKey().getNumberAccount())
          .accountType(AccountType.get(accountRecord.getAccountType()))
          .generalBalance(accountRecord.getGeneralBalance())
          .state(AccountState.get(accountRecord.getState()))
          .movements(List.ofAll(accountRecord.getMovementRecords()).map(this::toDomain))
          .build();
    }

    default AccountRecord toRecord(Account account) {
        return AccountRecord.builder()
          .accountKey(new AccountKey(account.getClientId(), account.getNumberAccount()))
          .clientName(account.getClientName())
          .accountType(account.getAccountType().name())
          .generalBalance(account.getGeneralBalance())
          .state(account.getState().name())
          .movementRecords(List.<MovementRecord>empty().toJavaList())
          .build();
    }
}
