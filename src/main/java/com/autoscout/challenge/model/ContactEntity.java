package com.autoscout.challenge.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long listingId;

    private Long contactDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getListingId() {
        return listingId;
    }

    public void setListingId(Long listingId) {
        this.listingId = listingId;
    }

    public Long getContactDate() {
        return contactDate;
    }

    public void setContactDate(Long contactDate) {
        this.contactDate = contactDate;
    }

    @Override
    public String toString() {
        return "ContactEntity{" +
                "listingId=" + listingId +
                ", contactDate=" + contactDate +
                '}';
    }
}
