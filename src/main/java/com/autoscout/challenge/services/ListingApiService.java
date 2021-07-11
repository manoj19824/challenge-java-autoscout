package com.autoscout.challenge.services;

import com.autoscout.challenge.exceptions.MappingException;
import com.autoscout.challenge.model.ContactEntity;
import com.autoscout.challenge.model.ContactListingReport;
import com.autoscout.challenge.model.ListingEntity;
import com.autoscout.challenge.model.SellerType;
import com.autoscout.challenge.repository.ContactApiRepository;
import com.autoscout.challenge.repository.ListingApiRepository;
import com.autoscout.challenge.util.CsvHelper;
import com.autoscout.challenge.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.YearMonth;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
public class ListingApiService {

    private final Logger logger = LoggerFactory.getLogger(ListingApiService.class);

    private final ListingApiRepository listingApiRepository;
    private final ContactApiRepository contactApiRepository;

    public ListingApiService(ListingApiRepository listingApiRepository, ContactApiRepository contactApiRepository) {
        this.listingApiRepository = listingApiRepository;
        this.contactApiRepository = contactApiRepository;
    }

    @Transactional
    public void processAndSaveFile(MultipartFile file, CsvHelper csvHelper) throws IOException, MappingException {
        logger.info("Processing started for the file {}", file.getOriginalFilename());
        //Delete the previous records and insert the new incoming records
        if (file.getOriginalFilename().equalsIgnoreCase("listings.csv")) {
            listingApiRepository.deleteAll();
            List<ListingEntity> result = listingApiRepository.saveAll(csvHelper.csvToListings(file.getInputStream()));
            if (result.size() > 0) {
                logger.info("{} listing records successfully stored in the database", result.size());
            }
        }
        if (file.getOriginalFilename().equalsIgnoreCase("contacts.csv")) {
            contactApiRepository.deleteAll();
            List<ContactEntity> result = contactApiRepository.saveAll(csvHelper.csvToContacts(file.getInputStream()));
            if (result.size() > 0) {
                logger.info("{} contacts records successfully stored in the database", result.size());
            }
        }
    }

    public Map<SellerType, String> getAvgListingPrice() {
        List<ListingEntity> listingEntities = listingApiRepository.findAll();
        Map<SellerType, Double> avgDataOnSellerType =
                listingEntities
                        .stream()
                        .collect(groupingBy(ListingEntity::getSellerType, averagingDouble(ListingEntity::getPrice)));
        return avgDataOnSellerType
                .entrySet()
                .stream()
                .collect(toMap(
                        e -> e.getKey(),
                        e -> Utility.priceFormat(e.getValue())
                ));
    }

    public Map<String, String> getPercentageDistByMake() {
        List<ListingEntity> listingEntities = listingApiRepository.findAll();
        Map<String, Double> groupByMakeUnsorted =
                listingEntities
                        .stream()
                        .collect(groupingBy(ListingEntity::getMake, Collectors.collectingAndThen(Collectors.counting(), n -> 100.0 * n / listingEntities.size())));
        return groupByMakeUnsorted
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(toMap(e -> e.getKey(), e -> Utility.percentageFormat(e.getValue()), (e1, e2) -> e2, LinkedHashMap::new));

    }

    public double getAvgPriceOfContactsListing() {
        List<ListingEntity> listingEntities = listingApiRepository.findAll();
        Map<Long, Long> listingContactCountMap = contactApiRepository
                .findAll()
                .stream()
                .collect(groupingBy(ContactEntity::getListingId, counting()));
        logger.info("Inside getAvg price of contacts listing..{}", listingContactCountMap);
        DoubleSummaryStatistics listingStats =
                listingEntities
                        .stream()
                        .filter(it -> listingContactCountMap.containsKey(it.getId()))
                        .collect(Collectors.summarizingDouble(ListingEntity::getPrice));
        double avgPriceForContactListing = listingStats.getAverage();
        //I did not get the actual use case to understand so just calculated the average.
        logger.info("Result is coming as {}", avgPriceForContactListing);
        return avgPriceForContactListing;
    }

    private Optional<ListingEntity> getListingObject(Long id, List<ListingEntity> listingEntities) {
        return listingEntities
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public Map<YearMonth, List<ContactListingReport>> getContactListing() {

        List<ListingEntity> listingEntities = listingApiRepository.findAll();

        Map<YearMonth, TreeMap<Long, Long>> listingContactCountMap = contactApiRepository.findAll()
                .stream()
                .collect(
                        Collectors.groupingBy(x -> Utility.convertToYearMonth(x.getContactDate()), TreeMap::new,
                                Collectors.groupingBy(x -> x.getListingId(), TreeMap::new, Collectors.counting())
                        ));
        Map<YearMonth, Map<Long, Long>> topFiveBasedOnContactDate =
                listingContactCountMap
                        .entrySet()
                        .stream()
                        .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().entrySet().stream()
                                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                                .limit(5)
                                .collect(Collectors.toMap(it -> it.getKey(), it -> it.getValue(), (e1, e2) -> e2, LinkedHashMap::new))));

        logger.info("sorted map is coming as {}", topFiveBasedOnContactDate);

        AtomicReference<AtomicInteger> atomicInteger = new AtomicReference<>();

        return topFiveBasedOnContactDate.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> {
                            atomicInteger.set(new AtomicInteger(1));
                            return e.getKey();
                        },
                        e -> e.getValue().entrySet().stream().map(it -> {
                            Optional<ListingEntity> listingEntity = getListingObject(it.getKey(), listingEntities);
                            if (listingEntity.isPresent()) {
                                return new ContactListingReport(atomicInteger.get().getAndIncrement(),
                                        listingEntity.get().getId(),
                                        listingEntity.get().getMake(),
                                        Utility.priceFormat(listingEntity.get().getPrice()),
                                        Utility.mileageFormat(listingEntity.get().getMileage()), it.getValue());
                            }
                            return null;
                        }).collect(toList())
                ));
    }
}
