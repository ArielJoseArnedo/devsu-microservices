package co.com.devsu.clients.infrastructure.events;

import co.com.devsu.clients.domain.models.Client;

public class ClientRegisted implements Event {

    private final Client client;

    public ClientRegisted(Client client) {
        this.client = client;
    }

    @Override
    public String getName() {
        return "ClientRegisted";
    }

    @Override
    public Client getMessage() {
        return client;
    }
}
