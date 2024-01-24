package com.login.logintest.customer;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface CustomerRepository {
    Optional<Customer> findByEmail(String email);
}
