package com.ecobill.ecobill.domain.entities;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Invoice")
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "qr_code", unique = true)
    private Long qrCode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_commercial_register", referencedColumnName = "commercial_register")
    private EPREntity epr;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_tax_number", referencedColumnName = "tax_number")
    private EPREntity eprTaxNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_phone_number", referencedColumnName = "phone_number")
    private CustomerEntity customer;

    private Date creationDate;

    private Long totalAmount;

}
