package co.com.devsu.movements.domain.models;

import io.vavr.collection.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import static co.com.devsu.movements.infrastructure.util.OptionUtil.updateOrElse;

@Getter
@Builder
@RequiredArgsConstructor
public class Account {
    private final String clientId;
    private final String clientName;
    private final String numberAccount;
    private final AccountType accountType;
    private final double generalBalance;
    private final AccountState state;
    private final List<Movement> movements;

    public Account update(Account account) {
        return Account.builder()
          .clientId(updateOrElse(clientId, account.getClientId()))
          .clientName(updateOrElse(clientName, account.getClientName()))
          .numberAccount(updateOrElse(numberAccount, account.getNumberAccount()))
          .accountType(updateOrElse(accountType, account.getAccountType(), AccountType.UNKNOWN))
          .generalBalance(updateOrElse(generalBalance, account.getGeneralBalance(), 0.0))
          .state(updateOrElse(state, account.getState(), AccountState.UNKNOWN))
          .movements(movements)
          .build();
    }

    public Account updateBalance(double generalBalance) {
        return Account.builder()
          .clientId(clientId)
          .clientName(clientName)
          .numberAccount(numberAccount)
          .accountType(accountType)
          .generalBalance(updateOrElse(generalBalance, generalBalance, 0.0))
          .state(getState())
          .movements(movements)
          .build();
    }
}
