package co.com.devsu.clients.infrastructure.events;

import co.com.devsu.clients.domain.models.Client;

public class ClientDeleted implements Event {
    private final Client client;

    public ClientDeleted(Client client) {
        this.client = client;
    }

    @Override
    public String getName() {
        return "ClientDeleted";
    }

    @Override
    public Client getMessage() {
        return client;
    }
}
