package com.autoscout.challenge.model;

import java.io.Serializable;

public class ContactListingReport implements Serializable {

    private long ranking;
    private long listingId;
    private String make;
    private String sellingPrice;
    private String mileage;
    private long totalAmtContacts;

    public ContactListingReport(long ranking, long listingId, String make, String sellingPrice, String mileage, long totalAmtContacts) {
        this.ranking = ranking;
        this.listingId = listingId;
        this.make = make;
        this.sellingPrice = sellingPrice;
        this.mileage = mileage;
        this.totalAmtContacts = totalAmtContacts;
    }

    public long getRanking() {
        return ranking;
    }

    public void setRanking(long ranking) {
        this.ranking = ranking;
    }

    public long getListingId() {
        return listingId;
    }

    public void setListingId(long listingId) {
        this.listingId = listingId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public long getTotalAmtContacts() {
        return totalAmtContacts;
    }

    public void setTotalAmtContacts(long totalAmtContacts) {
        this.totalAmtContacts = totalAmtContacts;
    }

    @Override
    public String toString() {
        return "ContactListingReport{" +
                "ranking=" + ranking +
                ", listingId=" + listingId +
                ", make='" + make + '\'' +
                ", sellingPrice='" + sellingPrice + '\'' +
                ", mileage='" + mileage + '\'' +
                ", totalAmtContacts=" + totalAmtContacts +
                '}';
    }
}
