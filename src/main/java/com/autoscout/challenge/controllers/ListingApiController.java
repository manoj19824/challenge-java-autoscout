package com.autoscout.challenge.controllers;

import com.autoscout.challenge.dto.ResponseMessage;
import com.autoscout.challenge.model.ContactListingReport;
import com.autoscout.challenge.model.SellerType;
import com.autoscout.challenge.services.ListingApiService;
import com.autoscout.challenge.util.CsvHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@Tag(name = "task", description = "Auto-scout listing API")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ListingApiController {

    private final Logger logger = LoggerFactory.getLogger(ListingApiController.class);

    private final CsvHelper csvHelper;
    private final ListingApiService apiService;

    public ListingApiController(CsvHelper csvHelper, ListingApiService apiService) {
        this.csvHelper = csvHelper;
        this.apiService = apiService;
    }

    @Operation(summary = "Upload multi part file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Uploaded successfully",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content)
    })
    @PostMapping(value = "/csv/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        logger.info("File name coming as {}",file.getName());
        String message = "";
        if(file.isEmpty()){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Please upload file with data and correct format"));
        }
        if (csvHelper.hasCSVFormat(file)) {
            try {
                apiService.processAndSaveFile(file,csvHelper);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/reports/avg-listing-selling-price")
    public ResponseEntity<Map<SellerType, String>> getAvgListingPrice() {
        try {
            Map<SellerType, String> avgListingPrice = apiService.getAvgListingPrice();
            return new ResponseEntity<>(avgListingPrice, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/reports/distribution")
    public ResponseEntity<Map<String, String>> getPercentageDistByMake() {
        try {
            Map<String, String> percentageDistByMake = apiService.getPercentageDistByMake();
            return new ResponseEntity<>(percentageDistByMake, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/reports/most-contacted-avg-price")
    public ResponseEntity<Double> getAvgPriceOfContactsListing() {
        try {
            Double avgPrice = apiService.getAvgPriceOfContactsListing();
            return new ResponseEntity<>(avgPrice, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reports/contact-listings")
    public ResponseEntity<Map<YearMonth,List<ContactListingReport>>> getContactListing() {
        try {
            Map<YearMonth,List<ContactListingReport>> contactListing = apiService.getContactListing();
            return new ResponseEntity<>(contactListing, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
