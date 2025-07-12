package com.sullivan.disc.repository;


import com.sullivan.disc.model.Disc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiscRepository extends JpaRepository<Disc, Long> {

    List<Disc> findByContactsLastName(String lastName);

    List<Disc> findByContactsPhoneNumber(String phoneNumber);

    List<Disc> findAll();

    List<Disc> findByReturnedTrue();

    List<Disc> findBySoldTrue();

    boolean deleteById(int id);
}
