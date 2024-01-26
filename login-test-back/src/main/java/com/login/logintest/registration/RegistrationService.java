package com.login.logintest.registration;

import com.login.logintest.customer.Customer;
import com.login.logintest.customer.CustomerRole;
import com.login.logintest.customer.CustomerService;
import com.login.logintest.registration.token.ConfirmationToken;
import com.login.logintest.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final CustomerService customerService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
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

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        customerService.enableCustomer(
                confirmationToken.getCustomer().getEmail());
        return "confirmed";
    }
}