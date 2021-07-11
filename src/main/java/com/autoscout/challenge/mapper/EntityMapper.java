package com.autoscout.challenge.mapper;

import com.autoscout.challenge.exceptions.MappingException;
import com.autoscout.challenge.model.ContactEntity;
import com.autoscout.challenge.model.ListingEntity;
import com.autoscout.challenge.model.SellerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class EntityMapper<T> {

    private final Logger logger = LoggerFactory.getLogger(EntityMapper.class);

    public List<T> mapToEntities(Map<Integer, Map<String, String>> data, Class classType) throws MappingException {
        List<T> resultEntities = new ArrayList<>();
        try {
            data.entrySet().stream().forEach(excelData -> {
                Map<String, String> valueMap = excelData.getValue();
                if (classType.getDeclaredFields().length == valueMap.size()) {
                    ListingEntity listingEntity = new ListingEntity();
                    ContactEntity contactEntity = new ContactEntity();
                    valueMap.entrySet().stream().forEach(it -> {
                        String key = it.getKey();
                        String value = it.getValue();
                        logger.info("Key is {}",key);
                        switch (key) {
                            case "listings:id":
                                listingEntity.setId(Long.parseLong(value));
                                break;
                            case "listings:make":
                                listingEntity.setMake(value);
                                break;
                            case "listings:price":
                                listingEntity.setPrice(Double.parseDouble(value));
                                break;
                            case "listings:mileage":
                                listingEntity.setMileage(Double.parseDouble(value));
                                break;
                            case "listings:seller_type":
                                listingEntity.setSellerType(SellerType.valueOf(value));
                                break;
                            case "contacts:listing_id":
                                contactEntity.setListingId(Long.parseLong(value));
                                break;
                            case "contacts:contact_date":
                                contactEntity.setContactDate(Long.parseLong(value));
                                break;
                            default:
                                break;
                        }
                    });
                    if (classType == ListingEntity.class) {
                        resultEntities.add((T) listingEntity);
                    }
                    if (classType == ContactEntity.class) {
                        resultEntities.add((T) contactEntity);
                    }
                }
            });
        } catch (Exception ex) {
            throw new MappingException("Exception occurred while mapping to entities", ex);
        }
        return resultEntities;
    }
}
