package co.com.devsu.movements.infrastructure.persistence.movements;

import co.com.devsu.movements.infrastructure.persistence.accounts.AccountKey;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class MovementKey implements Serializable {

    AccountKey accountKey;

    @Column(name = "movement_id")
    private String id;
}
