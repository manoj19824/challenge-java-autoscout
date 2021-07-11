package com.autoscout.challenge.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class ListingEntity {

    @Id
    private Long id;

    private String make;

    private double price;

    private double mileage;

    private SellerType sellerType;

    public ListingEntity() {
    }

    public ListingEntity(Long id, String make, double price, double mileage, SellerType sellerType) {
        this.id = id;
        this.make = make;
        this.price = price;
        this.mileage = mileage;
        this.sellerType = sellerType;
    }

    @OneToMany(
            cascade = CascadeType.ALL
    )
    private List<ContactEntity> contactEntities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public SellerType getSellerType() {
        return sellerType;
    }

    public void setSellerType(SellerType sellerType) {
        this.sellerType = sellerType;
    }

    @Override
    public String toString() {
        return "ListingEntity{" +
                "id=" + id +
                ", make='" + make + '\'' +
                ", price=" + price +
                ", mileage=" + mileage +
                ", sellerType=" + sellerType +
                ", contactEntities=" + contactEntities +
                '}';
    }
}
