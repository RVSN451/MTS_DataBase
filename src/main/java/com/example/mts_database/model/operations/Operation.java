package com.example.mts_database.model.operations;


import com.example.mts_database.model.cards.TransferAmount;
import com.example.mts_database.model.cards.PaymentCard;
import com.example.mts_database.model.frontInterface.Transfer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
@Entity
@NoArgsConstructor

public class Operation implements Serializable {

    public static final String VERIFICATION_CODE = "0000";

    @EmbeddedId
    private OperationId operationId;

    @Temporal(TemporalType.DATE)
    @Column(updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Date createDate;

    @Temporal(TemporalType.TIME)
    @Column(updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Date createTime;

    private transient PaymentCard cardFrom;

    private String cardFromNumber;

    private transient PaymentCard cardTo;

    private String cardToNumber;

    private TransferAmount amount;

    private BigDecimal comission;


    private boolean checkOperation;

    public Operation(Transfer transfer) {
        this.operationId = new OperationId();
        this.cardFromNumber = transfer.getCardFromNumber();
        this.cardToNumber = transfer.getCardToNumber();
        this.amount = transfer.getTransferAmount();
        this.comission = BigDecimal.valueOf(0);
    }
}
