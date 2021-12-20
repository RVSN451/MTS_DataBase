package com.example.mts_database.repository;

import com.example.mts_database.model.operations.Operation;
import com.example.mts_database.model.operations.OperationId;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
@PersistenceContext
public interface OperationRepository extends JpaRepository<Operation, String> {


    Optional<Operation> findByOperationId(OperationId id);
}
