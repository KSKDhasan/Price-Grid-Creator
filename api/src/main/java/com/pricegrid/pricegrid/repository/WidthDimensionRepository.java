package com.pricegrid.pricegrid.repository;

import com.pricegrid.pricegrid.model.WidthDimension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WidthDimensionRepository extends JpaRepository<WidthDimension, Long> {
    Optional<WidthDimension> findByWidthValue(int widthValue);  // Updated method name
    Optional<WidthDimension> findById(Long id);
    boolean existsByWidthValue(int widthValue); 
}
