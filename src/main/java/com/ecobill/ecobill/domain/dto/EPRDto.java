package com.ecobill.ecobill.domain.dto;

import com.ecobill.ecobill.domain.entities.SubscriptionEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EPRDto {

    private Long id;

    private Long commercialRegister;

    private Long taxNumber;

    private String name;

    private SubscriptionEntity subscription;

}
