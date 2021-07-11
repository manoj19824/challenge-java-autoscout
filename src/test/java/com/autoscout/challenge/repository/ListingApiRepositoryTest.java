package com.autoscout.challenge.repository;

import com.autoscout.challenge.model.ContactEntity;
import com.autoscout.challenge.model.ListingEntity;
import com.autoscout.challenge.model.SellerType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class ListingApiRepositoryTest {

    @Autowired
    private ListingApiRepository listingApiRepository;

    @Autowired
    private ContactApiRepository contactApiRepository;

    @Test
    public void testListingCreation(){
        ListingEntity entity = new ListingEntity();
        entity.setSellerType(SellerType.OTHER);
        entity.setMileage(5000);
        entity.setPrice(2300.33);
        entity.setId(1000l);
        entity.setMake("BMW");
        ListingEntity savedEnt = listingApiRepository.save(entity);
        assertNotNull(savedEnt.getId());
    }

    @Test
    public void testContactCreation(){
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setListingId(1000l);
        contactEntity.setContactDate(124222l);
        ContactEntity savedEnt = contactApiRepository.save(contactEntity);
        assertNotNull(savedEnt.getId());
    }
}
