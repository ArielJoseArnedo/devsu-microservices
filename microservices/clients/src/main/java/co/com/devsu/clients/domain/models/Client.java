package co.com.devsu.clients.domain.models;

import lombok.Builder;
import lombok.Getter;

import static co.com.devsu.clients.infrastructure.util.OptionUtil.updateOrElse;

@Getter
public non-sealed class Client extends Person {

    private final String clientId;
    private final String password;
    private final ClientState state;

    @Builder(builderClassName = "clientBuilder")
    public Client(String name, String identification, Gender gender, int age, String address, String phone, String clientId, String password, ClientState state) {
        super(name, identification, gender, age, address, phone);
        this.clientId = clientId;
        this.password = password;
        this.state = state;
    }

    public Client update(Client client) {
        return Client.builder()
          .clientId(updateOrElse(clientId, client.getClientId()))
          .password(updateOrElse(password, client.getPassword()))
          .state(updateOrElse(state, client.getState(), ClientState.UNKNOWN))
          .name(updateOrElse(name, client.getName()))
          .identification(updateOrElse(identification, client.getIdentification()))
          .gender(updateOrElse(gender, client.getGender(), Gender.UNKNOWN))
          .age(updateOrElse(age, client.getAge(), 0))
          .address(updateOrElse(address, client.getAddress()))
          .phone(updateOrElse(phone, client.getPhone()))
          .build();
    }
}
