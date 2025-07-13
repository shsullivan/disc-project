package com.sullivan.disc.repository;

import com.sullivan.disc.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {

    Optional<Manufacturer> findByManufacturer(String manufacturerName);
}
