package com.ecobill.ecobill.domain.dto;

import com.ecobill.ecobill.domain.entities.InvoiceEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceItemDto {

    private Long id;

    private String name;

    private Long price;

    private int quantity;

    private InvoiceEntity invoice;

}
