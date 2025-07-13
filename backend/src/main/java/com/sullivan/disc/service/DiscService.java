package com.sullivan.disc.service;

import com.sullivan.disc.dto.DiscCreateDTO;
import com.sullivan.disc.dto.DiscDTO;
import com.sullivan.disc.dto.DiscUpdateDTO;
import com.sullivan.disc.dto.ImportResultDTO;
import com.sullivan.disc.mapper.DiscMapper;
import com.sullivan.disc.model.Contact;
import com.sullivan.disc.model.Disc;
import com.sullivan.disc.model.Manufacturer;
import com.sullivan.disc.model.Mold;
import com.sullivan.disc.repository.ContactRepository;
import com.sullivan.disc.repository.DiscRepository;
import com.sullivan.disc.repository.ManufacturerRepository;
import com.sullivan.disc.repository.MoldRepository;
import com.sullivan.disc.util.DiscValidator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiscService {

    private final DiscRepository discRepository;
    private final DiscMapper discMapper;
    private final ContactRepository contactRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final MoldRepository moldRepository;

    public DiscService(DiscRepository discRepository,
                       DiscMapper discMapper,
                       ContactRepository contactRepository,
                       ManufacturerRepository manufacturerRepository,
                       MoldRepository moldRepository) {

        this.discRepository = discRepository;
        this.discMapper = discMapper;
        this.contactRepository = contactRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.moldRepository = moldRepository;
    }

    public DiscDTO createDisc(DiscCreateDTO dto) {
        // Check if contact already exists in DB
        Optional<Contact> existingContact = contactRepository.findByFirstNameAndLastNameAndPhoneNumber(
                dto.getContact().getFirstName(),
                dto.getContact().getLastName(),
                dto.getContact().getPhone()
        );
        Contact contact = existingContact.orElseGet(() -> {
            Contact newContact = new Contact();
            newContact.setFirstName(dto.getContact().getFirstName());
            newContact.setLastName(dto.getContact().getLastName());
            newContact.setPhoneNumber(dto.getContact().getPhone());
            return contactRepository.save(newContact);
        });

        // Retrieve manufacturer and mold by ID sent from UI
        Manufacturer manufacturer = manufacturerRepository.findById(dto.getManufacturer().getManufacturerId()).get();
        Mold mold = moldRepository.findById(dto.getMold().getMoldId()).get();

        // Convert DTO and other objects to Disc object
        Disc disc = discMapper.discCreateDTOtoDisc(dto, manufacturer, mold, contact);
        discRepository.save(disc);
        return discMapper.discToDiscDTO(disc);
    }

    // True and false values used in Controller to determine confirmation or failure message
    public boolean deleteDisc(Integer id) {
        if (discRepository.existsById(id)) {
            discRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<DiscDTO> getAllDiscs() {
        return discRepository.findAll()
                             .stream()
                             .map(discMapper::discToDiscDTO)
                             .collect(Collectors.toList());
    }

    public List<DiscDTO> getAllReturned() {
        return discRepository.findByReturnedTrue()
                             .stream()
                             .map(discMapper::discToDiscDTO)
                             .collect(Collectors.toList());
    }

    public List<DiscDTO> getAllSold() {
        return discRepository.findBySoldTrue()
                             .stream()
                             .map(discMapper::discToDiscDTO)
                             .collect(Collectors.toList());
    }

    public Optional<DiscDTO> findByID(int id) {
        return discRepository.findById(id).map(discMapper::discToDiscDTO);
    }

    public List<DiscDTO> findByLastName(String lastName) {
        return discRepository.findByContact_LastName(lastName).stream().map(discMapper::discToDiscDTO)
                .collect(Collectors.toList());
    }

    public List<DiscDTO> findByPhoneNumber(String phoneNumber) {
        return discRepository.findByContact_PhoneNumber(phoneNumber).stream().map(discMapper::discToDiscDTO)
                .collect(Collectors.toList());
    }

    public ImportResultDTO importFromTextFile(MultipartFile file) throws IOException {
        List<String> successes = new ArrayList<>();
        List<String> failures = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String line;
        int lineCount = 0;

        while ((line = reader.readLine()) != null) {
            lineCount++;
            String[] attributes = line.split("-");

            if (attributes.length != 13) {
                failures.add("Line " + lineCount + ": Wrong number of attributes.");
                continue;
            }

            try {
                String manufacturerName = DiscValidator.validateManufacturer(attributes[0].trim());
                String moldName = DiscValidator.validateMold(attributes[1].trim());
                String plastic = DiscValidator.validatePlastic(attributes[2].trim());
                String color = DiscValidator.validateColor(attributes[3].trim());
                int condition =  DiscValidator.validateCondition(attributes[4].trim());
                String description = DiscValidator.validateDescription(attributes[5].trim());
                String contactFirstName = DiscValidator.validateContactName(attributes[6].trim());
                String contactLastName = DiscValidator.validateContactName(attributes[7].trim());
                String contactPhone = DiscValidator.validateContactPhone(attributes[8].trim());
                String foundAt = DiscValidator.validateFoundAt(attributes[9].trim());
                boolean returned = DiscValidator.validateBooleanInput(attributes[10].trim(), "Returned");
                boolean sold = DiscValidator.validateBooleanInput(attributes[11].trim(), "Sold");
                BigDecimal MSRP = DiscValidator.validatePositiveBigDecimal(attributes[12].trim(), "MSRP");

                // Validate and assign manufacturer
                Manufacturer manufacturer = manufacturerRepository.findByManufacturer(manufacturerName)
                        .orElseThrow(() -> new RuntimeException("Manufacturer " + manufacturerName + " not found"));

                // Validate and assign mold
                Mold mold = moldRepository.findByMoldAndManufacturer(moldName, manufacturer)
                        .orElseThrow(() -> new RuntimeException("Mold " + moldName + " not found"));

                // Validate and assign contact information
                Optional<Contact> existingContact = contactRepository.findByFirstNameAndLastNameAndPhoneNumber(
                        contactFirstName,
                        contactLastName,
                        contactPhone
                );
                Contact contact = existingContact.orElseGet(() ->
                    contactRepository.save( new Contact(
                            null,
                            contactFirstName,
                            contactLastName,
                            contactPhone
                    )));

                Disc disc = new Disc(null,
                                     manufacturer,
                                     mold,
                                     plastic,
                                     color,
                                     condition,
                                     description,
                                     contact,
                                     foundAt,
                                     returned,
                                     sold,
                                     MSRP,
                                    null);

                discRepository.save(disc);
                successes.add("Line " + lineCount + ": Successfully imported. Disc: " + disc.getDiscID() + " created.");
            }
            catch (IllegalArgumentException e) {
                failures.add("Line " + lineCount + ": " + e.getMessage());
            }
        }
        return new ImportResultDTO(successes, failures);
    }
    public Optional<DiscDTO> updateDisc(Integer id, DiscUpdateDTO dto) {
        return discRepository.findById(id).map(existing -> {
            // Create and validate manufacturer
            Manufacturer manufacturer = manufacturerRepository.findById(dto.getManufacturer().getManufacturerId()).get();

            // Create and validate mold
            Mold mold = moldRepository.findById(dto.getMold().getMoldId()).get();

            // Check or create new contact
            Optional<Contact> existingContact = contactRepository.findByFirstNameAndLastNameAndPhoneNumber(
                    dto.getContact().getFirstName(),
                    dto.getContact().getLastName(),
                    dto.getContact().getPhone()
            );

            Contact contact = existingContact.orElseGet(() -> contactRepository.save(
                    new Contact(null,
                                dto.getContact().getFirstName(),
                                dto.getContact().getLastName(),
                                dto.getContact().getPhone())
            ));

            // Update all disc fields
            existing.setManufacturer(manufacturer);
            existing.setMold(mold);
            existing.setPlastic(dto.getPlastic());
            existing.setColor(dto.getColor());
            existing.setCondition(dto.getCondition());
            existing.setDescription(dto.getDescription());
            existing.setContact(contact);
            existing.setFoundAt(dto.getFoundAt());
            existing.setReturned(dto.isReturned());
            existing.setSold(dto.isSold());
            existing.setMsrp(dto.getMSRP());

            // Save disc
            return discMapper.discToDiscDTO(discRepository.save(existing));
        });
    }
}
