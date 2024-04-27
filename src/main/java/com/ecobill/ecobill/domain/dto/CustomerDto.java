package com.ecobill.ecobill.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {

    private Long id;

    private Long phoneNumber;

    private String name;

    private String username;

    private String password;

    private String email;
}


