package com.ecobill.ecobill.services;

import java.util.Map;

import com.ecobill.ecobill.domain.dto.loginRequestDto;
import com.ecobill.ecobill.domain.dto.signUpRequestDto;
import com.ecobill.ecobill.domain.entities.CustomerEntity;

public interface CustomerService {
    CustomerEntity createCustomer(Map<String, Object> customerMap);

    public String authenticateUser(loginRequestDto loginRequest);

    public void registerUser(signUpRequestDto signUpRequest);

}
