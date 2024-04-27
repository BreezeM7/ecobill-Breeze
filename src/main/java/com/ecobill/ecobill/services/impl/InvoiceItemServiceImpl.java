package com.ecobill.ecobill.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ecobill.ecobill.domain.entities.EPREntity;
import com.ecobill.ecobill.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import com.ecobill.ecobill.domain.entities.InvoiceEntity;
import com.ecobill.ecobill.domain.entities.InvoiceItemEntity;
import com.ecobill.ecobill.repositories.InvoiceItemRepository;
import com.ecobill.ecobill.services.InvoiceItemService;
import com.ecobill.ecobill.utils.ConversionUtils;

@Service
public class InvoiceItemServiceImpl implements InvoiceItemService {

    private InvoiceItemRepository invoiceItemRepository;
    private ConversionUtils conversionUtils;

    public InvoiceItemServiceImpl(InvoiceItemRepository invoiceItemRepository, ConversionUtils conversionUtils) {
        this.invoiceItemRepository = invoiceItemRepository;
        this.conversionUtils = conversionUtils;
    }

    @Override
    public void createInvoiceItem(List<Map<String, Object>> invoiceItemsList, InvoiceEntity invoiceEntity) {
        List<InvoiceItemEntity> invoiceItems = new ArrayList<InvoiceItemEntity>();

        if (invoiceItemsList != null) {
            for (Map<String, Object> item : invoiceItemsList) {
                InvoiceItemEntity invoiceItemEntity = InvoiceItemEntity.builder()
                        .name((String) item.get("name"))
                        .price(conversionUtils.doubleToLongConversion((Double) item.get("price")))
                        .quantity((Integer) item.get("quantity"))
                        .invoice(invoiceEntity)
                        .build();

                invoiceItems.add(invoiceItemEntity);

            }
            invoiceItemRepository.saveAll(invoiceItems);
        }

    }


}
