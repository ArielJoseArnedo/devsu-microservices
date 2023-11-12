package co.com.devsu.movements.domain.models;

import io.vavr.collection.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class Account {
    private final String clientId;
    private final String numberAccount;
    private final String accountType;
    private final double balance;
    private final String state;
    private final List<Movement> movements;

    public Account update(Account account) {
        return Account.builder()
          .clientId(clientId)
          .numberAccount(numberAccount)
          .accountType(account.getAccountType())
          .balance(balance)
          .state(account.getState())
          .movements(movements)
          .build();
    }

    public Account updateBalance(double balance) {
        return Account.builder()
          .clientId(clientId)
          .numberAccount(numberAccount)
          .accountType(accountType)
          .balance(balance)
          .state(getState())
          .movements(movements)
          .build();
    }
}
