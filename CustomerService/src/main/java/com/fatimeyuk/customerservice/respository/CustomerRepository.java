package com.fatimeyuk.customerservice.repository;

import com.fatimeyuk.customerservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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

    Customer findCustomerByNationalId(String id);
}
