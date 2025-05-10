package com.pricegrid.pricegrid.controller;

import com.pricegrid.pricegrid.model.PriceCell;
import com.pricegrid.pricegrid.model.WidthDimension;
import com.pricegrid.pricegrid.model.HeightDimension;
import com.pricegrid.pricegrid.service.PriceGridService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PriceGridController {

    private final PriceGridService priceGridService;

    ////////////////////////////////// Get Apis
    ////////////////////////////////// ///////////////////////////////////////////

    @GetMapping("/prices")
    public ResponseEntity<List<PriceCell>> getAllPrices() {
        System.out.println("Getting All Prices");
        return ResponseEntity.ok(priceGridService.getAllPriceCells());
    }

    @GetMapping("/widths")
    public ResponseEntity<List<WidthDimension>> getAllWidths() {
        return ResponseEntity.ok(priceGridService.getAllWidths());
    }

    @GetMapping("/heights")
    public ResponseEntity<List<HeightDimension>> getAllHeights() {

        // System.out.println("Getting Heights");

        return ResponseEntity.ok(priceGridService.getAllHeights());
    }

    // /////////////////////Post api- Add Row or Column api
    // //////////////////////////////
    @PostMapping("/addColumn")
    public ResponseEntity<Void> addColumn(@RequestParam int columnIndex, @RequestParam int rowCount) {
        priceGridService.addColumnWithZeros(columnIndex, rowCount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addRow")
    public ResponseEntity<Void> addRow(@RequestParam int RowIndex, @RequestParam int ColCount) {
        priceGridService.addRowWithZeros(RowIndex, ColCount);
        return ResponseEntity.ok().build();
    }

    /////////////////////////// Update Api
    /////////////////////////// ////////////////////////////////////////////////
    // Update Height
    @PutMapping("/updateHeight/{id}")
    public ResponseEntity<HeightDimension> updateHeight(
            @PathVariable Long id,
            @RequestBody HeightDimension updatedData) {
        HeightDimension updated = priceGridService.updateHeight(id, updatedData.getHeightValue());
        return ResponseEntity.ok(updated);
    }

    // Update Width
    @PutMapping("/updateWidth/{id}")
    public ResponseEntity<WidthDimension> updateWidth(
            @PathVariable Long id,
            @RequestBody WidthDimension updatedData) {
        WidthDimension updated = priceGridService.updateWidth(id, updatedData.getWidthValue());
        return ResponseEntity.ok(updated);
    }

    // update price based on width and height

    @PutMapping("/updatePrice/{height_id}/{width_id}")
    public ResponseEntity<PriceCell> updatePrice(
            @PathVariable int height_id,
            @PathVariable int width_id,
            @RequestParam double newPrice) {

        PriceCell updated = priceGridService.updatePrice(height_id, width_id, newPrice);
        return ResponseEntity.ok(updated);
    }

    // /////////////////////////////// Delete Api
    // /////////////////////////////////////////////////

    @DeleteMapping("/deleteRow/{rowId}")
    public ResponseEntity<Void> deleteRow(@PathVariable Long rowId) {
        priceGridService.deleteRowByRowId(rowId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteColum/{colId}")
    public ResponseEntity<Void> deleteColumn(@PathVariable Long colId) {
        priceGridService.deleteColumnByColId(colId);
        return ResponseEntity.noContent().build();
    }
    
}
