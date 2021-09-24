package com.fatimeyuk.customerservice.respository;

import com.fatimeyuk.customerservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("SELECT " +
            "  CASE " +
            "   WHEN " +
            "       COUNT(c)>0 " +
            "   THEN " +
            "       TRUE " +
            "   ELSE " +
            "       FALSE " +
            "   END " +
            "FROM Customer c " +
            "WHERE c.nationalId = ?1")
    boolean selectExistsNationalId(String nationalId);

    @Query(value = "SELECT count(c) FROM Customer c WHERE c.phoneNumber = ?1")
    int selectExistsPhoneNumber(String phoneNumber);


    Optional<Customer> findCustomerByNationalId(String id);

    void deleteByNationalId(String nationalId);
}
