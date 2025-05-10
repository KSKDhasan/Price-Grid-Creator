package com.pricegrid.pricegrid.repository;

import com.pricegrid.pricegrid.model.HeightDimension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeightDimensionRepository extends JpaRepository<HeightDimension, Long> {
    Optional<HeightDimension> findByHeightValue(int heightValue);  
    Optional<HeightDimension> findHeightById(Long id);
}
