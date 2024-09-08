package com.k1ng1nsu.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.k1ng1nsu.model.Contact;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {

}
