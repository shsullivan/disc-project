package com.sullivan.disc.controller;

import com.sullivan.disc.dto.DiscCreateDTO;
import com.sullivan.disc.dto.DiscDTO;
import com.sullivan.disc.dto.ImportResultDTO;
import com.sullivan.disc.model.Disc;
import com.sullivan.disc.service.DiscService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5174") // Vite dev server port
@RestController
@RequestMapping("/api/discs")
public class DiscController {

    private final DiscService discService;

    public DiscController(DiscService discService) {
        this.discService = discService;
    }

    @PostMapping
    public ResponseEntity<DiscDTO> createDisc(@RequestBody @Valid DiscCreateDTO dto) {
        DiscDTO createdDTO = discService.createDisc(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDTO);
    }

    @GetMapping
    public List<DiscDTO> getAllDiscs() {
        return discService.getAllDiscs();
    }

    @GetMapping("/returned")
    public List<DiscDTO> getAllReturnedDiscs() {
        return discService.getAllReturned();
    }

    @GetMapping("/sold")
    public List<DiscDTO> getAllSoldDiscs() {
        return discService.getAllSold();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscDTO> getDiscById(@PathVariable int id) {
        Optional<DiscDTO> disc = discService.findByID(id);
        return disc.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/lastname")
    public List<DiscDTO> findByLastName(@RequestParam String lastName) {
        return discService.findByLastName(lastName);
    }

    @GetMapping("/search/phone")
    public List<DiscDTO> findByPhone(@RequestParam String phone) {
        return discService.findByPhoneNumber(phone);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DiscDTO> deleteDiscById(@PathVariable int id) {
        return discService.deleteDisc(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/import")
    public ResponseEntity<ImportResultDTO> importFromFile(@RequestParam("file") MultipartFile file) throws IOException {
        ImportResultDTO resultDTO = discService.importFromTextFile(file);
        return ResponseEntity.ok(resultDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Disc> updateDisc(@PathVariable int id, @RequestBody Disc updatedDisc) {
        return discService.updateDisc(id, updatedDisc)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
