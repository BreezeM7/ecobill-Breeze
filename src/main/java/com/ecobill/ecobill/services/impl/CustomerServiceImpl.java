package com.ecobill.ecobill.services.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.ecobill.ecobill.domain.dto.loginRequestDto;
import com.ecobill.ecobill.domain.dto.signUpRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobill.ecobill.domain.entities.CustomerEntity;
import com.ecobill.ecobill.repositories.CustomerRepository;
import com.ecobill.ecobill.services.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerEntity createCustomer(Map<String, Object> customerMap) {
        HashMap<String, Object> customerHashMap = new HashMap<>(customerMap);

        CustomerEntity customerEntity = CustomerEntity.builder()
                .phoneNumber((Long) customerHashMap.get("phone_number"))
                .build();

        Optional<CustomerEntity> customerEntityOptional = customerRepository
                .findByPhoneNumber(customerEntity.getPhoneNumber());

        if (!customerEntityOptional.isPresent()) {
            return customerRepository.save(customerEntity);
        } else {
            return customerEntityOptional.get();
        }

    }

    public void registerUser(signUpRequestDto signUpRequest) {
        // Create a new user's account
        CustomerEntity user = new CustomerEntity();
        user.setName(signUpRequest.getName());
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());

        try {
            user.setPhoneNumber(Long.valueOf(signUpRequest.getPhoneNumber()));
        } catch (NumberFormatException e) {
            // Handle the exception
            System.out.println("Invalid phone number format.");
            return;
        }

        customerRepository.save(user);
    }


    public String authenticateUser(loginRequestDto loginRequest) {
        // Fetch the user from the database using the provided phone number
        Optional<CustomerEntity> userOptional = customerRepository.findByPhoneNumber(Long.valueOf(loginRequest.getPhoneNumber()));

        if (!userOptional.isPresent()) {
            // User not found with the provided phone number
            return "User not found.";
        }

        CustomerEntity user = userOptional.get();

        // Check if the provided password matches the one in the database
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            // Provided password doesn't match the one in the database
            return "Invalid password.";
        }

        // User is authenticated, return a simple message
        return "User authenticated successfully.";
    }

}
