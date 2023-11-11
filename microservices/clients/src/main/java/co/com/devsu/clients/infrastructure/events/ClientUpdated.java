package co.com.devsu.clients.infrastructure.events;

import co.com.devsu.clients.domain.models.Client;

public class ClientUpdated implements Event {
    private final Client client;

    public ClientUpdated(Client client) {
        this.client = client;
    }

    @Override
    public String getName() {
        return "ClientUpdated";
    }

    @Override
    public Client getMessage() {
        return client;
    }
}
