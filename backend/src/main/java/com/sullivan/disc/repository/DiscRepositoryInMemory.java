package com.sullivan.disc.repository;

import com.sullivan.disc.model.Disc;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
@Repository
public class DiscRepositoryInMemory implements DiscRepository {

    // Attributes
    // Internal storage hashmap for this no DB iteration
    private final Map<Integer, Disc> discRepo =  new ConcurrentHashMap<>();
    // Iterative counter to simulate how a DB would handle this as a count up PK
    private final AtomicInteger idGenerator = new AtomicInteger(1);


    @Override
    public Disc save(Disc disc) {
        if (disc.getDiscID() == 0) {
            int newDiscID = idGenerator.getAndIncrement();
            disc.setDiscID(newDiscID);
        }
        discRepo.put(disc.getDiscID(), disc);
        return disc;
    }

    @Override
    public Optional<Disc> findById(int id) {
        return Optional.ofNullable(discRepo.get(id));
    }

    @Override
    public List<Disc> findByContactLastName(String lastName) {
        return discRepo.values().stream()
                .filter(disc -> disc.getContactLastName() != null &&
                        disc.getContactLastName().toLowerCase().contains(lastName.toLowerCase()))
                .collect(Collectors.toList());

    }

    @Override
    public List<Disc> findByContactPhoneNumber(String phoneNumber) {
        return discRepo.values().stream().filter(disc ->
                phoneNumber.equals(disc.getContactPhone())).toList();
    }

    @Override
    public List<Disc> findAll() {
        return new ArrayList<>(discRepo.values());
    }

    @Override
    public List<Disc> findReturnedDiscs() {
        return discRepo.values().stream().filter(Disc::isReturned).toList();
    }

    @Override
    public List<Disc> findSoldDiscs() {
        return discRepo.values().stream().filter(Disc::isSold).toList();
    }

    @Override
    public boolean deleteById(int id) {
        Disc disc = discRepo.get(id);
        if (disc != null) {
            discRepo.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean markAsReturned(int id) {
        Disc disc = discRepo.get(id);
        if (disc != null && !disc.isReturned()) {
            disc.setReturned(true);
            return true;
        }
        return false;
    }

    @Override
    public boolean markAsSold(int id) {
        Disc disc = discRepo.get(id);
        if (disc != null && !disc.isSold()) {
            disc.setSold(true);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateContactInformation(int id, String firstName, String lastName, String phoneNumber) {
        Disc disc = discRepo.get(id);
        if (disc != null) {
            disc.setContactFirstName(firstName);
            disc.setContactLastName(lastName);
            disc.setContactPhone(phoneNumber);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateMSRP(int id, double newMSRP) {
        Disc disc = discRepo.get(id);
        if (disc != null && newMSRP > 0) {
            disc.setMSRP(newMSRP);
            return true;
        }
        return false;
    }
}
