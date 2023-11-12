package co.com.devsu.movements.infrastructure.events;

import co.com.devsu.movements.domain.models.Account;

public class AccountUpdated implements Event {

    private final Account account;

    public AccountUpdated(Account account) {
        this.account = account;
    }

    @Override
    public String getName() {
        return "AccountUpdated";
    }

    @Override
    public Account getMessage() {
        return account;
    }
}
