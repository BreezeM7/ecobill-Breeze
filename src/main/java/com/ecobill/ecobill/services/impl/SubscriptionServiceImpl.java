package com.ecobill.ecobill.services.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecobill.ecobill.domain.entities.SubscriptionEntity;
import com.ecobill.ecobill.repositories.SubscriptionRepository;
import com.ecobill.ecobill.services.SubscriptionService;
import com.ecobill.ecobill.utils.ConversionUtils;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private SubscriptionRepository subscriptionRepository;
    private ConversionUtils conversionUtils;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, ConversionUtils conversionUtils) {
        this.subscriptionRepository = subscriptionRepository;
        this.conversionUtils = conversionUtils;
    }

    @Override
    public SubscriptionEntity createSubscription(Map<String, Object> subscriptionMap) {
        HashMap<String, Object> subscriptionHashMap = new HashMap<>(subscriptionMap);
        SubscriptionEntity subscriptionEntity = SubscriptionEntity.builder()
                .subscriptionNumber(
                        conversionUtils
                                .integerToLongConversion((Integer) subscriptionHashMap.get("subscription_number")))
                .build();

        Optional<SubscriptionEntity> subscriptionEntityOptional = subscriptionRepository
                .findBySubscriptionNumber(subscriptionEntity.getSubscriptionNumber());

        if (!subscriptionEntityOptional.isPresent()) {
            return subscriptionRepository.save(subscriptionEntity);
        } else {
            return subscriptionEntityOptional.get();
        }

    }

}
