package com.fatimeyuk.creditscoreservice.repository;

import com.fatimeyuk.creditscoreservice.entity.CreditRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditScoreRepository extends JpaRepository<CreditRequest, Long> {
}
