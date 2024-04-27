package com.ecobill.ecobill.services;

import java.util.Map;

import com.ecobill.ecobill.domain.entities.SubscriptionEntity;

public interface SubscriptionService {
    SubscriptionEntity createSubscription(Map<String, Object> subscriptionMap);

}
