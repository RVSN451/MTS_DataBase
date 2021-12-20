package com.example.mts_database.model.operations;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import java.io.Serializable;
import java.util.UUID;

@Data
@Embeddable
public class OperationId implements Serializable {
    @Setter(AccessLevel.NONE)
    private String operationId;

    public OperationId(){
        this.operationId = UUID.randomUUID().toString();
    }

    public OperationId(String operationId){
        this.operationId = operationId;
    }

}
