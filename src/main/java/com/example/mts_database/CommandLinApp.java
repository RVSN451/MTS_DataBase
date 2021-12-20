package com.example.mts_database;


import com.example.mts_database.model.cards.CardAmount;
import com.example.mts_database.model.cards.DebitCard;

import com.example.mts_database.repository.DebitCardRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.*;

@Component
@PersistenceContext
public class CommandLinApp implements CommandLineRunner {

    public static final BigDecimal percent = BigDecimal.valueOf(1);


    private DebitCardRepository debitCardRepository;


    public CommandLinApp(DebitCardRepository repository){
        this.debitCardRepository = repository;
    }

    @Override
    public void run(String... args) {

        DebitCard dk1 = new DebitCard("Vasiliy", "Kostakov",
                "1111111111111111", "111", "11/22",
                new CardAmount(BigDecimal.valueOf(100000), "RUB"));
        debitCardRepository.save(dk1);

        DebitCard dk2 = new DebitCard("Natalia", "Kostakova",
                "2222222222222222", "222", "11/22",
                new CardAmount(BigDecimal.valueOf(1000000), "RUB"));
        debitCardRepository.save(dk2);
    }
}
