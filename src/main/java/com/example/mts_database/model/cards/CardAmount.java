package com.example.mts_database.model.cards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Data
@Setter(AccessLevel.NONE)
@NoArgsConstructor
@Embeddable
public class CardAmount {
    BigDecimal value;
    String currency;


    public CardAmount(BigDecimal value, String currency) {
        this.currency = currency;
        this.value = value;
    }

    public void subtractAmount (BigDecimal subtracter) {
        value = value.subtract(subtracter);
    }

    public void addAmount (BigDecimal adder) {
        value = value.add(adder);
    }
}
