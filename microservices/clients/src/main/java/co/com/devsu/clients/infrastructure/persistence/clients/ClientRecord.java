package co.com.devsu.clients.infrastructure.persistence.clients;

import co.com.devsu.clients.infrastructure.persistence.persons.PersonRecord;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "clients")
public class ClientRecord {

    @Id
    @Column(name = "client_id")
    private String clientId;

    private String password;

    private String state;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "identification_id", referencedColumnName = "identification")
    private PersonRecord personRecord;
}
