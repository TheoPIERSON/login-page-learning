package com.login.logintest.registration;

import com.login.logintest.customer.Customer;
import com.login.logintest.customer.CustomerRole;
import com.login.logintest.customer.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final CustomerService customerService;
    private final EmailValidator emailValidator;
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail){
            throw new IllegalStateException("email not valid");
        }
        return customerService.signUpCustomer(
                new Customer(
                        request.getFirstname(),
                        request.getLastname(),
                        request.getBirthdate(),
                        request.getEmail(),
                        request.getPhone_number(),
                        request.getPassword(),


                        CustomerRole.USER
                )
        );
    }
}
