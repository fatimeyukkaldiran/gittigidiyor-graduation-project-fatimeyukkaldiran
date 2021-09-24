package com.fatimeyuk.creditapplicationservice.repository;

import com.fatimeyuk.creditapplicationservice.entity.CreditRequestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditAppRepository extends JpaRepository<CreditRequestResult, Long> {

    List<CreditRequestResult> findAllByNationalId(String nationalId);
}
