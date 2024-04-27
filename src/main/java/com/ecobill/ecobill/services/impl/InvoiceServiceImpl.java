package com.ecobill.ecobill.services.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ecobill.ecobill.domain.dto.InvoiceDto;
import com.ecobill.ecobill.mappers.impl.InvoiceMapperImpl;
import org.springframework.stereotype.Service;

import com.ecobill.ecobill.domain.entities.CustomerEntity;
import com.ecobill.ecobill.domain.entities.EPREntity;
import com.ecobill.ecobill.domain.entities.InvoiceEntity;
import com.ecobill.ecobill.repositories.InvoiceRepository;
import com.ecobill.ecobill.services.InvoiceService;
import com.ecobill.ecobill.utils.ConversionUtils;


@Service
public class InvoiceServiceImpl implements InvoiceService {

    private InvoiceRepository invoiceRepository;
    private ConversionUtils conversionUtils;
    private EPRServiceImpl eprService;
    private InvoiceMapperImpl invoiceMapper;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository
            , ConversionUtils conversionUtils
            , EPRServiceImpl eprService
            , InvoiceMapperImpl invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.conversionUtils = conversionUtils;
        this.eprService = eprService;
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    public InvoiceEntity createInvoice(Map<String, Object> invoiceMap, EPREntity eprEntity,
                                       CustomerEntity customerEntity) {
        HashMap<String, Object> invoiceHashMap = new HashMap<>(invoiceMap);
        InvoiceEntity invoiceEntity = null;

        try {
            invoiceEntity = InvoiceEntity.builder()
                    .qrCode(conversionUtils.integerToLongConversion((Integer) invoiceHashMap.get("qr_code")))
                    .epr(eprEntity).eprTaxNumber(eprEntity).customer(customerEntity)
                    .creationDate(conversionUtils.StringToDateConversion((String) invoiceHashMap.get("created_at")))
                    .totalAmount(conversionUtils.doubleToLongConversion((Double) invoiceHashMap.get("total_amount")))
                    .build();
            return invoiceRepository.save(invoiceEntity);

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    @Override
    public List<InvoiceDto> getByEPR(String name) {
        EPREntity epr = eprService.getEPRByName(name);
        try {
            List<InvoiceDto> invoiceDtos = invoiceRepository.findAllByEpr(epr)
                    .stream()
                    .map(invoiceMapper::mapTo)
                    .collect(Collectors.toList());
            return invoiceDtos;
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public List<InvoiceDto> getByAmountBetween(Long min, Long max) {
        try {
            return invoiceRepository.findAllByTotalAmountBetween(min, max).stream().map(invoiceMapper::mapTo).collect(Collectors.toList());
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public List<InvoiceDto> getByEPRCategory(String category) {
        List<EPREntity> eprs = eprService.getByCategory(category);
        List<InvoiceDto> invoices = new ArrayList<>();

        for (EPREntity epr : eprs) {
            invoices.addAll(getByEPR(epr.getName()));
        }
        return invoices;
    }

    @Override
    public List<InvoiceDto> getByCreationDateBetweenAndUserNumber(Long userNumber, Timestamp lower, Timestamp upper) {
        try {
            return invoiceRepository.findAllByCustomerPhoneNumberAndCreationDateBetween(userNumber, lower, upper)
                    .stream()
                    .map(invoiceMapper::mapTo)
                    .collect(Collectors.toList());
        } catch (NullPointerException e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<InvoiceDto> getInvoicesForUserInRange(Long userNumber, int start, int end) {
        int limit = end - start + 1;
        int offset = start - 1; // Assuming the first invoice has an index of 1
        try {
            return invoiceRepository.findInvoicesByCustomerPhoneNumberWithOffset(userNumber, limit, offset)
                    .stream()
                    .map(invoiceMapper::mapTo)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<String> printUserStatistics(Long userNumber, int numberOfMonths) {
        Timestamp lower = Timestamp.valueOf(LocalDateTime.now().minusMonths(numberOfMonths));
        Timestamp upper = Timestamp.valueOf(LocalDateTime.now());

        List<InvoiceDto> invoices = getByCreationDateBetweenAndUserNumber(userNumber, lower, upper);

        double averageSpent = invoices.stream()
                .mapToDouble(InvoiceDto::getTotalAmount)
                .average()
                .orElse(0.0);

        EPREntity mostVisitedCompany = invoices.stream()
                .collect(Collectors.groupingBy(InvoiceDto::getEpr, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        String mostVisitedCompanyName = mostVisitedCompany != null ? mostVisitedCompany.getName() : "No company visited";

        double thisMonthSpending = invoices.stream()
                .filter(invoice -> invoice.getCreationDate().after(Timestamp.valueOf(LocalDateTime.now().minusMonths(1))))
                .mapToDouble(InvoiceDto::getTotalAmount)
                .sum();

        double lastMonthSpending = invoices.stream()
                .filter(invoice -> invoice.getCreationDate().before(Timestamp.valueOf(LocalDateTime.now().minusMonths(1))))
                .mapToDouble(InvoiceDto::getTotalAmount)
                .sum();

        List<String> userStatistics = new ArrayList<>();
        userStatistics.add(String.valueOf(userNumber));
        userStatistics.add(String.format("%.2f", averageSpent));
        userStatistics.add(mostVisitedCompanyName);
        userStatistics.add(String.format("%.2f", thisMonthSpending));
        userStatistics.add(String.format("%.2f", lastMonthSpending));
        userStatistics.add(String.format("%.2f", (thisMonthSpending - lastMonthSpending)));

        return userStatistics;
    }
}
