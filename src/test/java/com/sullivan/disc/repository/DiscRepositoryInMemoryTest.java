package com.sullivan.disc.repository;

import com.sullivan.disc.model.Disc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class DiscRepositoryInMemoryTest {

    private DiscRepositoryInMemory discRepo;

    @BeforeEach
    void setup() {
        discRepo = new DiscRepositoryInMemory();
    }

    private Disc generateTestDisc() {
        return new Disc(0, "Innova", "Wraith", "Star Color Glow", "Blue", 8,
                "My favorite disc", "John", "Smith", "3348675309",
                "Springfield", false, false, 19.99);
    }

    @Test
    void TestSaveDiscAndFindByID() {
        Disc disc = generateTestDisc();
        discRepo.save(disc);

        Optional<Disc> found = discRepo.findById(disc.getDiscID());
        assertTrue(found.isPresent());
        assertEquals("Innova", found.get().getManufacturer());
    }

    @Test
    void TestFindByContactLastName() {
        Disc disc = generateTestDisc();
        discRepo.save(disc);
        List<Disc> result = discRepo.findByContactLastName(disc.getContactLastName());
        assertTrue(result.contains(disc));
        assertEquals(disc.getContactLastName(), result.get(0).getContactLastName());
    }

    @Test
    void TestFindByContactPhoneNumber() {
        Disc disc = generateTestDisc();
        discRepo.save(disc);
        List<Disc> result = discRepo.findByContactPhoneNumber(disc.getContactPhone());
        assertTrue(result.contains(disc));
        assertEquals(disc.getContactPhone(), result.get(0).getContactPhone());
    }

    @Test
    void TestFindAllDiscs() {
        Disc disc = generateTestDisc();
        discRepo.save(disc);
        List<Disc> result = discRepo.findAll();
        assertTrue(result.contains(disc));
        assertEquals(1, result.size());
    }

    @Test
    void TestFindReturnedDisc() {
        Disc disc = generateTestDisc();
        disc.setReturned(true);
        discRepo.save(disc);
        List<Disc> result = discRepo.findReturnedDiscs();
        assertTrue(result.contains(disc));
        assertEquals(1, result.size());
    }

    @Test
    void TestFindSoldDisc() {
        Disc disc = generateTestDisc();
        disc.setSold(true);
        discRepo.save(disc);
        List<Disc> result = discRepo.findSoldDiscs();
        assertTrue(result.contains(disc));
        assertEquals(1, result.size());
    }

    @Test
    void TestDeleteByID() {
        Disc disc = generateTestDisc();
        discRepo.save(disc);
        boolean deleted = discRepo.deleteById(disc.getDiscID());
        assertTrue(deleted);
        assertFalse(discRepo.findById(disc.getDiscID()).isPresent());
    }

    @Test
    void testMarkAsReturned() {
        Disc disc = generateTestDisc();
        discRepo.save(disc);
        boolean result = discRepo.markAsReturned(disc.getDiscID());
        assertTrue(result);
        assertTrue(discRepo.findById(disc.getDiscID()).get().isReturned());
    }

    @Test
    void testMarkAsSold() {
        Disc disc = generateTestDisc();
        discRepo.save(disc);
        boolean result = discRepo.markAsSold(disc.getDiscID());
        assertTrue(result);
        assertTrue(discRepo.findById(disc.getDiscID()).get().isSold());
    }

    @Test
    void testUpdateContactInformation() {
        Disc disc = generateTestDisc();
        discRepo.save(disc);
        boolean result = discRepo.updateContactInformation(1, "Jimmy", "Johnson",
                "1234567890");
        assertTrue(result);
        assertEquals("Jimmy", disc.getContactFirstName());
        assertEquals("Johnson", disc.getContactLastName());
        assertEquals("1234567890", disc.getContactPhone());
    }

    @Test
    void testUpdateMSRP() {
        Disc disc = generateTestDisc();
        discRepo.save(disc);
        boolean result = discRepo.updateMSRP(disc.getDiscID(), 24.99);
        assertTrue(result);
        assertEquals(24.99, disc.getMSRP(), 0.0001);
    }
}
