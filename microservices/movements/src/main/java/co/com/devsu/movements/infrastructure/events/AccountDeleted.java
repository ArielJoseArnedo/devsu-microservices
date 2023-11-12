package co.com.devsu.movements.infrastructure.events;

import co.com.devsu.movements.domain.models.Account;

public class AccountDeleted implements Event {

    private final Account account;

    public AccountDeleted(Account account) {
        this.account = account;
    }

    @Override
    public String getName() {
        return "AccountDeleted";
    }

    @Override
    public Account getMessage() {
        return account;
    }
}
