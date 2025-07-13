package com.sullivan.disc.repository;

import com.sullivan.disc.model.Manufacturer;
import com.sullivan.disc.model.Mold;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MoldRepository extends JpaRepository<Mold, Integer> {

    Optional<Mold> findByMoldAndManufacturer(String mold, Manufacturer manufacturer);
}
