package com.autoscout.challenge.service;

import com.autoscout.challenge.model.ContactEntity;
import com.autoscout.challenge.model.ContactListingReport;
import com.autoscout.challenge.model.ListingEntity;
import com.autoscout.challenge.model.SellerType;
import com.autoscout.challenge.repository.ContactApiRepository;
import com.autoscout.challenge.repository.ListingApiRepository;
import com.autoscout.challenge.services.ListingApiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.YearMonth;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ListingApiServiceTest {

    @Mock
    private ListingApiRepository listingApiRepository;

    @Mock
    private ContactApiRepository contactApiRepository;

    @InjectMocks
    private ListingApiService listingApiService;

    @Test
    public void testGetAvgListingPrice() throws IOException {
        Map<SellerType, String> expected = new HashMap<>();
        expected.put(SellerType.PRIVATE,"€23000.0,-");
        expected.put(SellerType.DEALER,"€21000.0,-");
        expected.put(SellerType.OTHER,"€29000.0,-");
        Mockito.when(listingApiRepository.findAll()).thenReturn(getListingEntity());
        Map<SellerType, String> result = listingApiService.getAvgListingPrice();
        assertNotNull(result);
        assertEquals(expected,result);
    }
    @Test
    public void testGetPercentageDist() throws IOException {
        Mockito.when(listingApiRepository.findAll()).thenReturn(getListingEntity());
        Map<String, String> result = listingApiService.getPercentageDistByMake();
        assertNotNull(result);
    }

    @Test
    public void testGetContractListing() throws IOException {
        Mockito.when(listingApiRepository.findAll()).thenReturn(getListingEntity());
        Mockito.when(contactApiRepository.findAll()).thenReturn(getContactEntity());
        Map<YearMonth, List<ContactListingReport>> result = listingApiService.getContactListing();
        assertNotNull(result);
    }

    @Test
    public void testGetContractListingWithNoContracts() throws IOException {
        Mockito.when(listingApiRepository.findAll()).thenReturn(getListingEntity());
        Mockito.when(contactApiRepository.findAll()).thenReturn(Collections.emptyList());
        Map<YearMonth, List<ContactListingReport>> result = listingApiService.getContactListing();
        System.out.println(result);
        assertTrue(result.isEmpty());
    }

    private List<ListingEntity> getListingEntity(){
        List<ListingEntity> listingEntities = new ArrayList<>();

        ListingEntity entity = new ListingEntity();
        entity.setId(1000l);
        entity.setMake("BMW");
        entity.setPrice(23000);
        entity.setMileage(50000);
        entity.setSellerType(SellerType.PRIVATE);

        ListingEntity entity1 = new ListingEntity();
        entity1.setId(1001l);
        entity1.setMake("FIAT");
        entity1.setPrice(21000);
        entity1.setMileage(28000);
        entity1.setSellerType(SellerType.DEALER);

        ListingEntity entity2 = new ListingEntity();
        entity2.setId(1003l);
        entity2.setMake("AUDI");
        entity2.setPrice(29000);
        entity2.setMileage(38000);
        entity2.setSellerType(SellerType.OTHER);

        listingEntities.add(entity);
        listingEntities.add(entity1);
        listingEntities.add(entity2);
        return listingEntities;
    }
    private List<ContactEntity> getContactEntity(){
        List<ContactEntity> contactEntities = new ArrayList<>();
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setListingId(1000l);
        contactEntity.setContactDate(1586924858000l);
        ContactEntity contactEntity1 = new ContactEntity();
        contactEntity1.setListingId(1001l);
        contactEntity1.setContactDate(1586924858000l);
        ContactEntity contactEntity2 = new ContactEntity();
        contactEntity2.setListingId(1002l);
        contactEntity2.setContactDate(1586924858000l);
        contactEntities.add(contactEntity);
        contactEntities.add(contactEntity1);
        contactEntities.add(contactEntity2);
        return contactEntities;
    }
}
