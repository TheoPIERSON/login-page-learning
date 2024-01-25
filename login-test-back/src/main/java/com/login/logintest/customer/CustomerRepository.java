package com.login.logintest.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);


    @Transactional
    @Modifying
    @Query("UPDATE Customer a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableCustomer(String email);

}
