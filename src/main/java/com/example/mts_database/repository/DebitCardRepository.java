package com.example.mts_database.repository;

import com.example.mts_database.model.cards.DebitCard;
import com.example.mts_database.model.cards.PaymentCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
@PersistenceContext
public interface DebitCardRepository extends JpaRepository<DebitCard, String> {

    Optional<DebitCard> findByCardNumber(String s);
}
