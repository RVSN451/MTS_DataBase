package com.example.mts_database.controller;

import com.example.mts_database.model.exceptions.ErrorConfirmation;
import com.example.mts_database.model.exceptions.ErrorInputData;
import com.example.mts_database.model.exceptions.ErrorTransfer;
import com.example.mts_database.model.exceptions.ErrorDataBase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandlers {
    @ExceptionHandler(ErrorInputData.class)
    public ResponseEntity<String> errorInputDataEx (ErrorInputData e) {
        System.out.println(e.getLocalizedMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ErrorConfirmation.class)
    public ResponseEntity<String> errorConfirmationEx (ErrorConfirmation e) {
        System.out.println(e.getLocalizedMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorTransfer.class)
    public ResponseEntity<String> errorTransferEx (ErrorTransfer e) {
        System.out.println(e.getLocalizedMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ErrorDataBase.class)
    public ResponseEntity<String> errorVerificationCodeEx (ErrorDataBase e) {
        System.out.println(e.getLocalizedMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}