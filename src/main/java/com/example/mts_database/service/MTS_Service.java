package com.example.mts_database.service;

import com.example.mts_database.model.cards.DebitCard;
import com.example.mts_database.model.cards.PaymentCard;
import com.example.mts_database.model.exceptions.ErrorConfirmation;
import com.example.mts_database.model.exceptions.ErrorDataBase;
import com.example.mts_database.model.exceptions.ErrorInputData;
import com.example.mts_database.model.exceptions.ErrorTransfer;
import com.example.mts_database.repository.DebitCardRepository;
import com.example.mts_database.repository.OperationRepository;
import com.example.mts_database.model.frontInterface.ConfirmOperation;
import com.example.mts_database.model.frontInterface.Transfer;
import com.example.mts_database.model.operations.Operation;
import com.example.mts_database.model.operations.OperationId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Service
public class MTS_Service {


    public static final Logger log = LoggerFactory.getLogger(MTS_Service.class);

    private final DebitCardRepository debitCardRepository;
    private final OperationRepository operationRepository;

    public MTS_Service(DebitCardRepository repository, OperationRepository operationRepository) {
        this.debitCardRepository = repository;
        this.operationRepository = operationRepository;
    }

    @SuppressWarnings("unused")
    public Operation transferMoneyCardToCard(Transfer transfer) {
        Operation operation = new Operation(transfer);
        operationRepository.save(operation);
        //Проверка на равенство номеров карт From & To
        try {
            long fromEqualTo = 1 / (Long.parseLong(transfer.getCardFromNumber()) -
                    Long.parseLong(transfer.getCardToNumber()));
        } catch (ArithmeticException a) {
            log.error("ErrorTransfer exception. CardFrom {} equal CardTo {}.",
                    operation.getCardFromNumber(), operation.getCardToNumber());
            throw new ErrorTransfer("ErrorTransfer exception. CardFrom equal CardTo.");
        }

        //Извлечение карт из репозитория, проверка CVV
        PaymentCard cardFrom = debitCardRepository
                .findByCardNumber(transfer.getCardFromNumber())
                .orElseThrow(() -> {
                    log.error("CardFromNumber {} was not found in the database",
                            transfer.getCardFromNumber());
                    return new ErrorInputData("Input data error. CardNumber incorrect.");
                });
        PaymentCard cardTo = debitCardRepository
                .findByCardNumber(operation.getCardToNumber())
                .orElseThrow(() -> {
                    log.error("CardToNumber {} was not found in the database",
                            transfer.getCardToNumber());
                    return new ErrorInputData("Input data error. CardNumber incorrect.");
                });
        if (cardFrom.checkCardCvv(transfer.getCardFromCVV())) {
            operation.setCardFrom(cardFrom);
            operation.setCardTo(cardTo);
        } else {
            log.error("Input data error. CVV {} incorrect.", transfer.getCardFromCVV());
            throw new ErrorInputData("Input data error. CVV incorrect.");
        }
        return operationRepository.save(operation);
    }


    @Transactional
    public OperationId checkVerification(ConfirmOperation confirmOperation) {

        //Извлечение операции из репозитория, проверка кода верификации
        Operation operation = operationRepository
                .findByOperationId(new OperationId(confirmOperation.getOperationId()))
                .orElseThrow(() -> {
                    log
                            .error("Confirmation operation error. OperationId {} incorrect",
                                    confirmOperation.getOperationId());
                    return new ErrorConfirmation("Confirmation operation error.");
                });
        if (!Operation.VERIFICATION_CODE.equals(confirmOperation.getCode())) {
            log.error("Confirmation operation error. Verification Code {} incorrect",
                    confirmOperation.getCode());
            throw new ErrorConfirmation("Verification Code error.");
        }
        //Извлечение карт из репозитория
        DebitCard cardFrom = debitCardRepository
                .findByCardNumber(operation.getCardFromNumber())
                .orElseThrow(() -> new ErrorDataBase("DataBase error"));

        DebitCard cardTo = debitCardRepository
                .findByCardNumber(operation.getCardToNumber())
                .orElseThrow(() -> new ErrorDataBase("DataBase error"));

        //Проверка достаточности средств для перевода/прорведение транзакции
        BigDecimal commission = cardFrom.subtractAmount(operation.getAmount().getValue());
        if (commission.compareTo(BigDecimal.valueOf(0)) > 0) {
            cardTo.addAmount(operation.getAmount().getValue());
            debitCardRepository.save(cardFrom);
            debitCardRepository.save(cardTo);
            operation.setCheckOperation(true);
            operation.setComission(commission);
            operationRepository.save(operation);
            log.info("CardFrom: {}, CardTo: {}, operationSum: {}, commissionSum {}, CheckOperation: {}, ",
                    cardFrom.getCardNumber(), operation.getComission(), cardTo.getCardNumber(),
                    operation.getAmount().getValue(), operation.isCheckOperation());
        } else {

           log.error("PaymentCard {} balance is insufficient to complete the transaction",
                    cardFrom.getCardNumber());
            throw new ErrorConfirmation("PaymentCard balance is insufficient to complete the transaction");
        }

        return operation.getOperationId();
    }
}
