package com.ecobill.ecobill.controllers;

import com.ecobill.ecobill.domain.dto.InvoiceDto;
import com.ecobill.ecobill.domain.dto.loginRequestDto;
import com.ecobill.ecobill.domain.dto.signUpRequestDto;
import com.github.javafaker.Faker;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecobill.ecobill.domain.entities.CustomerEntity;
import com.ecobill.ecobill.domain.entities.EPREntity;
import com.ecobill.ecobill.domain.entities.InvoiceEntity;
import com.ecobill.ecobill.domain.entities.SubscriptionEntity;
import com.ecobill.ecobill.services.CustomerService;
import com.ecobill.ecobill.services.EPRService;
import com.ecobill.ecobill.services.InvoiceItemService;
import com.ecobill.ecobill.services.InvoiceService;
import com.ecobill.ecobill.services.SubscriptionService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
public class InvoiceController {

    private InvoiceService invoiceService;
    private EPRService eprService;
    private SubscriptionService subscriptionService;
    private CustomerService customerService;
    private InvoiceItemService invoiceItemService;

    public InvoiceController(InvoiceService invoiceService, EPRService eprService,
            SubscriptionService subscriptionService, CustomerService customerService,
            InvoiceItemService invoiceItemService) {
        this.invoiceService = invoiceService;
        this.eprService = eprService;
        this.subscriptionService = subscriptionService;
        this.customerService = customerService;
        this.invoiceItemService = invoiceItemService;
    }

    @PostMapping(path = "/invoices")
    public ResponseEntity<Void> createInvoice(@RequestBody Map<String, Object> requestBody) {
        try {
            Map<String, Object> eprMap = (Map<String, Object>) requestBody.get("epr");
            Map<String, Object> customerMap = (Map<String, Object>) requestBody.get("customer");
            Map<String, Object> invoiceMap = (Map<String, Object>) requestBody.get("invoice");
            Map<String, Object> subscriptionMap = (Map<String, Object>) requestBody.get("subscription");
            List<Map<String, Object>> invoiceItemsList = (List<Map<String, Object>>) requestBody.get("invoice_items");

            SubscriptionEntity subscriptionEntity = subscriptionService.createSubscription(subscriptionMap);
            EPREntity eprEntity = eprService.createEpr(eprMap, subscriptionEntity);
            CustomerEntity customerEntity = customerService.createCustomer(customerMap);
            InvoiceEntity invoiceEntity = invoiceService.createInvoice(invoiceMap, eprEntity, customerEntity);
            invoiceItemService.createInvoiceItem(invoiceItemsList, invoiceEntity);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping(path ="invoice")
    public List<InvoiceDto> findInvoice(
             @RequestParam(name = "min",required = false) Long lower
            ,@RequestParam(name="max",required = false) Long upper) {
        lower=lower==null?0:lower;
        upper=upper==null?Long.MAX_VALUE:upper;
       return invoiceService.getByAmountBetween(lower, upper);
    }
    @GetMapping(path = "invoice/category")
    public List<InvoiceDto> CategorizeInvoices(@RequestParam(name="name") String category){

        return invoiceService.getByEPRCategory(category);
    }
    @GetMapping(path = "invoice/company")
    public List<InvoiceDto> findInvoiceByEPR(@RequestParam(name="name") String name){
        return invoiceService.getByEPR(name);
    }

    @GetMapping(path = "invoice/getUserInvoiceDateBetween")
    public List<InvoiceDto> findInvoiceByDateBetween(Long userNumber, Timestamp lower, Timestamp upper){
        return invoiceService.getByCreationDateBetweenAndUserNumber(userNumber, lower, upper);
    }

    @GetMapping(path = "invoice/findUserInvoiceInRange")
    public List<InvoiceDto> findUserInvoiceInRange(Long userNumber, int start, int end){
        return invoiceService.getInvoicesForUserInRange(userNumber, start, end);
    }

    @GetMapping(path = "invoice/getUserStatistics")
    public List<String> getUserStatistics(Long userNumber, int numberOfMonths){
        return invoiceService.printUserStatistics(userNumber, numberOfMonths);
    }
}