package com.sullivan.disc.repository;


import com.sullivan.disc.model.Disc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiscRepository extends JpaRepository<Disc, Integer> {

    List<Disc> findByContact_LastName(String contactLastName);

    List<Disc> findByContact_PhoneNumber(String contactPhoneNumber);

    List<Disc> findByReturnedTrue();

    List<Disc> findBySoldTrue();
}
