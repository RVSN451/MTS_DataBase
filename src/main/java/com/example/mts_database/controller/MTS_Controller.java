package com.example.mts_database.controller;

import com.example.mts_database.model.frontInterface.ConfirmOperation;
import com.example.mts_database.model.frontInterface.Transfer;
import com.example.mts_database.model.operations.OperationId;
import com.example.mts_database.service.MTS_Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
public class MTS_Controller {

    private MTS_Service service;

    public MTS_Controller(MTS_Service service) {
        this.service = service;
    }


    @CrossOrigin
    @PostMapping("/transfer")
    public OperationId transferMoneyCardToCard(@Valid @RequestBody Transfer transfer) {
        return service.transferMoneyCardToCard(transfer).getOperationId();
    }

    @CrossOrigin
    @PostMapping("/confirmOperation")
    @Transactional
    public OperationId checkVerification(@Valid @RequestBody ConfirmOperation confirmOperation) {
        return service.checkVerification(confirmOperation);
    }
}