package com.sullivan.disc.service;

import com.sullivan.disc.dto.DiscCreateDTO;
import com.sullivan.disc.dto.DiscDTO;
import com.sullivan.disc.dto.ImportResultDTO;
import com.sullivan.disc.mapper.DiscMapper;
import com.sullivan.disc.model.Disc;
import com.sullivan.disc.repository.DiscRepository;
import com.sullivan.disc.util.DiscValidator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiscService {

    private final DiscRepository discRepository;
    private final DiscMapper discMapper;

    public DiscService(DiscRepository discRepository, DiscMapper discMapper) {
        this.discRepository = discRepository;
        this.discMapper = discMapper;
    }

    public DiscDTO createDisc(DiscCreateDTO dto) {
        Disc disc = discMapper.discCreateDTOtoDisc(dto, 0);
        discRepository.save(disc);
        return discMapper.discToDiscDTO(disc);
    }

    public boolean deleteDisc(int id) {
        return discRepository.deleteById(id);
    }

    public List<DiscDTO> getAllDiscs() {
        return discRepository.findAll().stream().map(discMapper::discToDiscDTO).collect(Collectors.toList());
    }

    public List<DiscDTO> getAllReturned() {
        return discRepository.findReturnedDiscs().stream().map(discMapper::discToDiscDTO).collect(Collectors.toList());
    }

    public List<DiscDTO> getAllSold() {
        return discRepository.findSoldDiscs().stream().map(discMapper::discToDiscDTO).collect(Collectors.toList());
    }

    public Optional<DiscDTO> findByID(int id) {
        return discRepository.findById(id).map(discMapper::discToDiscDTO);
    }

    public List<DiscDTO> findByLastName(String lastName) {
        return discRepository.findByContactLastName(lastName).stream().map(discMapper::discToDiscDTO)
                .collect(Collectors.toList());
    }

    public List<DiscDTO> findByPhoneNumber(String phoneNumber) {
        return discRepository.findByContactPhoneNumber(phoneNumber).stream().map(discMapper::discToDiscDTO)
                .collect(Collectors.toList());
    }

    public boolean markDiscReturned(int id) {
        return discRepository.findById(id).map(disc -> {
            disc.setReturned(true);
            discRepository.save(disc);
            return true;
        }).orElse(false);
    }

    public boolean markDiscSold(int id) {
        return discRepository.findById(id).map(disc ->  {
            disc.setSold(true);
            discRepository.save(disc);
            return true;
        }).orElse(false);
    }

    public boolean updateContactInformation(int id, String firstName, String lastName, String phoneNumber) {
        return discRepository.findById(id).map(disc -> {
            disc.setContactFirstName(firstName);
            disc.setContactLastName(lastName);
            disc.setContactPhone(phoneNumber);
            discRepository.save(disc);
            return true;
        }).orElse(false);
    }

    public boolean updateMSRP(int id, double msrp) {
        return discRepository.findById(id).map(disc ->  {
            disc.setMSRP(msrp);
            discRepository.save(disc);
            return true;
        }).orElse(false);
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
                int discID = 0;
                String manufacturer = DiscValidator.validateManufacturer(attributes[0].trim());
                String mold = DiscValidator.validateMold(attributes[1].trim());
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
                double MSRP = DiscValidator.validatePositiveDouble(attributes[12].trim(), "MSRP");

                Disc disc = new Disc(discID, manufacturer, mold, plastic, color, condition, description,
                                    contactFirstName, contactLastName, contactPhone, foundAt, returned, sold, MSRP);

                discRepository.save(disc);
                successes.add("Line " + lineCount + ": Successfully imported. Disc: " + disc.getDiscID() + " created.");
            }
            catch (IllegalArgumentException e) {
                failures.add("Line " + lineCount + ": " + e.getMessage());
            }
        }
        return new ImportResultDTO(successes, failures);
    }


}
