package com.example.mts_database.model.frontInterface;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class ConfirmOperation {
    @Setter(AccessLevel.NONE)
    private String operationId;
    @Setter(AccessLevel.NONE)
    private String code;

    @JsonCreator
    public ConfirmOperation(@JsonProperty("operationId") String operationId,
                            @JsonProperty ("code") String code){
        this.operationId = operationId;
        this.code = code;
    }

}