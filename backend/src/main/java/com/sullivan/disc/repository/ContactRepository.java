package com.sullivan.disc.repository;

import com.sullivan.disc.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

    // Query method to check if a contact already exists in the contacts DB table
    Optional<Contact> findByFirstNameAndLastNameAndPhoneNumber(String firstName, String  lastName, String phoneNumber);
}
