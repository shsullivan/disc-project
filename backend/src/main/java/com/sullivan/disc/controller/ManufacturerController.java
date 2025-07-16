package com.sullivan.disc.controller;

import com.sullivan.disc.dto.ManufacturerDTO;
import com.sullivan.disc.model.Manufacturer;
import com.sullivan.disc.util.CustomDataSourceManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/manufacturers")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class ManufacturerController {

    private final CustomDataSourceManager dataSourceManager;

    public ManufacturerController(CustomDataSourceManager dataSourceManager) {
        this.dataSourceManager = dataSourceManager;
    }

    @GetMapping
    public ResponseEntity<?> getAllManufacturers() {
        EntityManagerFactory emf = dataSourceManager.getEntityManagerFactory();
        if (emf == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Database connection has not been initialized.");
        }

        EntityManager em = emf.createEntityManager();
        List<Manufacturer> manufacturers = em.createQuery("SELECT m FROM Manufacturer m", Manufacturer.class)
                .getResultList();
        em.close();

        List<ManufacturerDTO> dtos = manufacturers.stream()
                .map(m -> new ManufacturerDTO(m.getManufacturerId(), m.getManufacturer()))
                .toList();

        return ResponseEntity.ok(dtos);
    }
}
