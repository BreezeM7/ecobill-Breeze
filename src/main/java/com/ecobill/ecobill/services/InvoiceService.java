package com.ecobill.ecobill.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.ecobill.ecobill.domain.dto.InvoiceDto;
import com.ecobill.ecobill.domain.entities.CustomerEntity;
import com.ecobill.ecobill.domain.entities.EPREntity;
import com.ecobill.ecobill.domain.entities.InvoiceEntity;

public interface InvoiceService {

    InvoiceEntity createInvoice(Map<String, Object> invoiceMap, EPREntity eprEntity, CustomerEntity customerEntity);
    List<InvoiceDto> getByEPR(String name);
    List<InvoiceDto> getByAmountBetween(Long min, Long max);
    List<InvoiceDto> getByEPRCategory(String category);

    public List<InvoiceDto> getByCreationDateBetweenAndUserNumber(Long userNumber, Timestamp lower, Timestamp upper);

    public List<InvoiceDto> getInvoicesForUserInRange(Long userNumber, int start, int end);

    public List<String> printUserStatistics(Long userNumber, int numberOfMonths);
}
