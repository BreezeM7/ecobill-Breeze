package com.ecobill.ecobill.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.ecobill.ecobill.domain.dto.EPRDto;
import com.ecobill.ecobill.domain.entities.EPREntity;
import com.ecobill.ecobill.mappers.Mapper;

@Component
public class EPRMapperImpl implements Mapper<EPREntity, EPRDto> {

    private ModelMapper modelMapper;

    public EPRMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public EPRDto mapTo(EPREntity eprEntity) {
        return modelMapper.map(eprEntity, EPRDto.class);
    }

    @Override
    public EPREntity mapFrom(EPRDto eprDto) {
        return modelMapper.map(eprDto, EPREntity.class);
    }

}
