package co.com.devsu.movements.infrastructure.persistence.accounts;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AccountKey implements Serializable {
    @Column(name = "client_id")
    private String clientId;

    @Column(name = "number_account")
    private String numberAccount;
}
