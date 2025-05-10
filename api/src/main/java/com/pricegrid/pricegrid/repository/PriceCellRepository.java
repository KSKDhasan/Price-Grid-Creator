package com.pricegrid.pricegrid.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pricegrid.pricegrid.model.PriceCell;

@Repository
public interface PriceCellRepository extends JpaRepository<PriceCell, Long> {

    List<PriceCell> findByHeight_HeightValue(int heightValue);

    List<PriceCell> findByWidth_Id(Long widthId);

    Optional<PriceCell> findByHeight_IdAndWidth_Id(int heightId, int widthId);

    List<PriceCell> findByHeight_Id(Long heightId);

}
