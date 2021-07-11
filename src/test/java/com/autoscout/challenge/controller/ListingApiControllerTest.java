package com.autoscout.challenge.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ListingApiControllerTest {

    @LocalServerPort
    private int port;

    private static boolean initialized = false;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void uploadCsvFiles() throws MalformedURLException {
        if(!initialized){
            System.out.println("inside not initialized");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            String endPoint =  new URL("http://localhost:" + port + "/api/csv/upload").toString();

            Arrays.asList("listings.csv","contacts.csv").stream().forEach(it->{
                MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
                body.add("file", getTestFile(it));
                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
                restTemplate.postForObject(endPoint, requestEntity, String.class);
            });
            initialized = true;
        }
    }

    @Test
    public void fetchAvgListingSellingPriceBasedOnSaleTypes() throws Exception {
        String endPoint =  new URL("http://localhost:" + port + "/api/reports/avg_listing_selling_price").toString();
        ResponseEntity<String> response = restTemplate.getForEntity(endPoint, String.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void fetchDistributionBasedOnMake() throws Exception {
        String endPoint =  new URL("http://localhost:" + port + "/api/reports/distribution").toString();
        ResponseEntity<String> response = restTemplate.getForEntity(endPoint, String.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void fetchMostContactedAvgPrice() throws Exception {
        String endPoint =  new URL("http://localhost:" + port + "/api/reports/most-contacted-avg-price").toString();
        ResponseEntity<String> response = restTemplate.getForEntity(endPoint, String.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void fetchContactListing() throws Exception {
        String endPoint =  new URL("http://localhost:" + port + "/api/reports/contact-listings-per-month").toString();
        ResponseEntity<String> response = restTemplate.getForEntity(endPoint, String.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    private FileSystemResource getTestFile(String fileName) {
        return new FileSystemResource(Path.of("src", "test", "resources", fileName));
    }
}
