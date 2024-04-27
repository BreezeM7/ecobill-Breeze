package com.ecobill.ecobill.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.ecobill.ecobill.domain.dto.InvoiceDto;
import com.ecobill.ecobill.domain.entities.InvoiceEntity;
import com.ecobill.ecobill.mappers.Mapper;

@Component
public class InvoiceMapperImpl implements Mapper<InvoiceEntity, InvoiceDto> {

    private ModelMapper modelMapper;

    public InvoiceMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public InvoiceDto mapTo(InvoiceEntity invoiceEntity) {
        return modelMapper.map(invoiceEntity, InvoiceDto.class);
    }

    @Override
    public InvoiceEntity mapFrom(InvoiceDto invoiceDto) {
        return modelMapper.map(invoiceDto, InvoiceEntity.class);
    }

}
