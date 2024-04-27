package com.ecobill.ecobill.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "EPR")
public class EPREntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "commercial_register", unique = true)
    private Long commercialRegister;

    @Column(name = "tax_number", unique = true)
    private Long taxNumber;

    private String name;

    @OneToOne
    @JoinColumn(name = "subscription_id")
    private SubscriptionEntity subscription;
    @Column(name= "category",nullable = false)
    private String category;

    @Column(name = "EnumCategory")
    private Category EnumCategory;

}
enum Category {
    RESTAURANT,
    CAFE,
    HOTEL,
    GAS_STATION,
    CAR_SHOP,
    CLOTHES
}
