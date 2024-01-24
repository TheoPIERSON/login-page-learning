package com.login.logintest.registration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Date birthdate;
    private String phone_number;
}
