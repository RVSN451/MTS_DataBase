package com.example.mts_database.model.frontInterface;

import com.example.mts_database.model.cards.TransferAmount;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;


@Data
@NoArgsConstructor
public class Transfer {
    @Pattern(regexp = "^[0-9]{16}$")
    private String cardFromNumber;

    @Pattern(regexp = ("^[0-9][0-2]+/[2-3][0-9]$"))
    private String cardFromValidTill;

    @Pattern(regexp = "^[0-9]{3}$")
    private String cardFromCVV;

    @Pattern(regexp = "^[0-9]{16}$")
    private String cardToNumber;

    TransferAmount transferAmount;


    @JsonCreator
    public Transfer(@JsonProperty("cardFromNumber") String cardFromNumber,
                    @JsonProperty("cardFromValidTill") String cardFromValidTill,
                    @JsonProperty("cardFromCVV") String cardFromCVV,
                    @JsonProperty("cardToNumber") String cardToNumber,
                    @JsonProperty("amount") TransferAmount amount) {

        this.cardFromNumber = cardFromNumber;
        this.cardFromValidTill = cardFromValidTill;
        this.cardFromCVV = cardFromCVV;
        this.cardToNumber = cardToNumber;
        this.transferAmount = amount;
    }
}

