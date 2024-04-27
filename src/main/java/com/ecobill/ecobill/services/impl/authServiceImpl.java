//package com.ecobill.ecobill.services.impl;
//
//import com.ecobill.ecobill.domain.dto.loginRequestDto;
//import com.ecobill.ecobill.domain.dto.signUpRequestDto;
//import com.ecobill.ecobill.domain.entities.CustomerEntity;
//import com.ecobill.ecobill.repositories.CustomerRepository;
//
//import java.util.Optional;
//
//public class authServiceImpl {
//
//    private CustomerRepository customerRepository;
//
//    public void registerUser(signUpRequestDto signUpRequest) {
//        // Create a new user's account
//        CustomerEntity user = new CustomerEntity();
//        user.setName(signUpRequest.getName());
//        user.setUsername(signUpRequest.getUsername());
//        user.setEmail(signUpRequest.getEmail());
//        user.setPassword(signUpRequest.getPassword());
//
//        try {
//            user.setPhoneNumber(Long.valueOf(signUpRequest.getPhoneNumber()));
//        } catch (NumberFormatException e) {
//            // Handle the exception
//            System.out.println("Invalid phone number format.");
//            return;
//        }
//
//        customerRepository.save(user);
//    }
//
//
//    public String authenticateUser(loginRequestDto loginRequest) {
//        // Fetch the user from the database using the provided phone number
//        Optional<CustomerEntity> userOptional = customerRepository.findByPhoneNumber(Long.valueOf(loginRequest.getPhoneNumber()));
//
//        if (!userOptional.isPresent()) {
//            // User not found with the provided phone number
//            return "User not found.";
//        }
//
//        CustomerEntity user = userOptional.get();
//
//        // Check if the provided password matches the one in the database
//        if (!user.getPassword().equals(loginRequest.getPassword())) {
//            // Provided password doesn't match the one in the database
//            return "Invalid password.";
//        }
//
//        // User is authenticated, return a simple message
//        return "User authenticated successfully.";
//    }
//}
