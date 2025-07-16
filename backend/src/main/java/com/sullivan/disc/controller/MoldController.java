package com.sullivan.disc.controller;

import com.sullivan.disc.dto.MoldDTO;
import com.sullivan.disc.model.Mold;
import com.sullivan.disc.util.CustomDataSourceManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/molds")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class MoldController {

    private final CustomDataSourceManager dataSourceManager;

    public MoldController(CustomDataSourceManager dataSourceManager) {
        this.dataSourceManager = dataSourceManager;
    }

    @GetMapping("/by-manufacturer/{manufacturerId}")
    public ResponseEntity<?> getMoldsByManufacturer(@PathVariable int manufacturerId) {
        EntityManagerFactory emf = dataSourceManager.getEntityManagerFactory();
        if (emf == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Database connection has not been initialized.");
        }

        EntityManager em = emf.createEntityManager();
        List<Mold> molds = em.createQuery("SELECT m FROM Mold m WHERE m.manufacturer.manufacturerId = :id", Mold.class)
                .setParameter("id", manufacturerId)
                .getResultList();
        em.close();

        List<MoldDTO> dtos = molds.stream()
                .map(m -> new MoldDTO(m.getMoldID(), m.getManufacturer().getManufacturerId() ,m.getMold()))
                .toList();

        return ResponseEntity.ok(dtos);
    }
}
