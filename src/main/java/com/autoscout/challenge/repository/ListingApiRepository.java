package com.autoscout.challenge.repository;

import com.autoscout.challenge.model.ListingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingApiRepository extends JpaRepository<ListingEntity, String> {

}
