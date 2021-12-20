package com.example.mts_database.model.cards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Embeddable;

@Data
@Setter(AccessLevel.NONE)
@NoArgsConstructor
@Embeddable
public class TransferAmount {
    Integer value;
    String currency;

    @JsonCreator
    public TransferAmount(@JsonProperty("value") Integer value,
                          @JsonProperty("currency") String currency) {
        this.currency = currency;
        this.value = value/100;
    }
}
