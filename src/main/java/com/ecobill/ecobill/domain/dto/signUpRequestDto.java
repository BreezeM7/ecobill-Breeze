package com.ecobill.ecobill.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class signUpRequestDto {
    private String email;
    private String name;
    private String password;
    private long phoneNumber;
    private String username;
    // getters and setters
}
