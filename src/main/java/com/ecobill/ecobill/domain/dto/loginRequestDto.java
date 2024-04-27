package com.ecobill.ecobill.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class loginRequestDto {
    private long phoneNumber;
    private String password;
    // getters and setters
}
