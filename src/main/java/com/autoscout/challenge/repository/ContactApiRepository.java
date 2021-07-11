package com.autoscout.challenge.repository;

import com.autoscout.challenge.model.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactApiRepository extends JpaRepository<ContactEntity, String> {

}
