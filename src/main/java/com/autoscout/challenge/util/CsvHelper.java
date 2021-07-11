package com.autoscout.challenge.util;

import com.autoscout.challenge.model.ContactEntity;
import com.autoscout.challenge.model.ListingEntity;
import com.autoscout.challenge.model.SellerType;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvHelper {

    public static String TYPE = "text/csv";

    public boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public List<ListingEntity> csvToListings(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<ListingEntity> listings = new ArrayList<ListingEntity>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                ListingEntity listing = new ListingEntity(
                        Long.parseLong(csvRecord.get("Id")),
                        csvRecord.get("make"),
                        Double.parseDouble(csvRecord.get("price")),
                        Double.parseDouble(csvRecord.get("mileage")),
                        SellerType.valueOf(csvRecord.get("seller_type").toUpperCase())
                );
                listings.add(listing);
            }
            return listings;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public List<ContactEntity> csvToContacts(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<ContactEntity> contacts = new ArrayList<ContactEntity>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                ContactEntity contact = new ContactEntity();
                contact.setContactDate( Long.parseLong(csvRecord.get("contact_date")));
                contact.setListingId(Long.parseLong(csvRecord.get("listing_id")));
                contacts.add(contact);
            }
            return contacts;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

}
