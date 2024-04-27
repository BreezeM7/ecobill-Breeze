package com.ecobill.ecobill.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecobill.ecobill.domain.entities.EPREntity;
import com.ecobill.ecobill.domain.entities.SubscriptionEntity;
import com.ecobill.ecobill.repositories.EPRRepository;
import com.ecobill.ecobill.services.EPRService;
import com.ecobill.ecobill.utils.ConversionUtils;

@Service
public class EPRServiceImpl implements EPRService {

    private EPRRepository eprRepository;
    private ConversionUtils conversionUtils;

    public EPRServiceImpl(EPRRepository eprRepository, ConversionUtils conversionUtils) {
        this.eprRepository = eprRepository;
        this.conversionUtils = conversionUtils;
    }

    @Override
    public EPREntity createEpr(Map<String, Object> eprMap, SubscriptionEntity subscriptionEntity) {
        HashMap<String, Object> eprHashMap = new HashMap<>(eprMap);
        EPREntity eprEntity = EPREntity.builder()
                .commercialRegister(
                        conversionUtils.integerToLongConversion((Integer) eprHashMap.get("commercial_register")))
                .taxNumber((Long) eprHashMap.get("tax_number"))
                .name((String) eprHashMap.get("name"))
                .category("category")
                .build();
        eprEntity.setSubscription(subscriptionEntity);

        Optional<EPREntity> eprEntityOptional = eprRepository
                .findByCommercialRegister(eprEntity.getCommercialRegister());

        if (!eprEntityOptional.isPresent()) {
            return eprRepository.save(eprEntity);
        } else {
            return eprEntityOptional.get();
        }
    }

    public EPREntity getEPRByName(String name) {
        return eprRepository.findByName(name);
    }
    public List<EPREntity> getByCategory(String category) {
        return eprRepository.findAllByCategoryIgnoreCase(category);
    }
}
