package com.sullivan.disc.repository;


import com.sullivan.disc.model.Disc;

import java.util.List;
import java.util.Optional;

public interface DiscRepository {

    Disc save(Disc disc);

    Optional<Disc> findById(int id);

    List<Disc> findByContactLastName(String lastName);

    List<Disc> findByContactPhoneNumber(String phoneNumber);

    List<Disc> findAll();

    List<Disc> findReturnedDiscs();

    List<Disc> findSoldDiscs();

    boolean deleteById(int id);

    boolean markAsReturned(int id);

    boolean markAsSold(int id);

    boolean updateContactInformation(int id, String firstName, String lastName, String phoneNumber);

    boolean updateMSRP(int id, double MSRP);
}
