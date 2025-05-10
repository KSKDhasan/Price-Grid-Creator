package com.pricegrid.pricegrid.service;

import com.pricegrid.pricegrid.model.PriceCell;
import com.pricegrid.pricegrid.model.WidthDimension;
import com.pricegrid.pricegrid.model.HeightDimension;
import com.pricegrid.pricegrid.repository.PriceCellRepository;
import com.pricegrid.pricegrid.repository.WidthDimensionRepository;
import com.pricegrid.pricegrid.repository.HeightDimensionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriceGridService {

    private final PriceCellRepository priceCellRepository;
    private final WidthDimensionRepository widthRepo;
    private final HeightDimensionRepository heightRepo;

    ////////////// Fetch All data //////////////////////////////////////////////////

    public List<PriceCell> getAllPriceCells() {
        return priceCellRepository.findAll();
    }

    public List<WidthDimension> getAllWidths() {
        return widthRepo.findAll();
    }

    public List<HeightDimension> getAllHeights() {
        return heightRepo.findAll();
    }

    // ----------------------
    // Add Width or Height
    // ----------------------
    public WidthDimension addWidth(int widthValue) {
        return widthRepo.save(new WidthDimension(null, widthValue));
    }

    public HeightDimension addHeight(int heightValue) {
        return heightRepo.save(new HeightDimension(null, heightValue));
    }

    // ----------------------
    // Delete full row or column
    // ----------------------

    public void deleteColumnByColId(Long widthId) {
        List<PriceCell> cells = priceCellRepository.findByWidth_Id(widthId);
        if (!cells.isEmpty()) {
            priceCellRepository.deleteAll(cells);
        }

        Optional<WidthDimension> width = widthRepo.findById(widthId);
        if (width.isPresent()) {
            widthRepo.delete(width.get());
        } else {
            throw new IllegalArgumentException("No column found with ID " + widthId);
        }
    }

    public void deleteRowByRowId(Long heightId) {
        List<PriceCell> cells = priceCellRepository.findByHeight_Id(heightId);
        if (!cells.isEmpty()) {
            priceCellRepository.deleteAll(cells);
        }

        Optional<HeightDimension> height = heightRepo.findById(heightId);
        if (height.isPresent()) {
            heightRepo.delete(height.get());
        } else {
            throw new IllegalArgumentException("No column found with ID " + heightId);
        }
    }

    //////////////////////////// Update services
//////////////////////////// /////////////////////////////////////////////////

    // Update Height
    public HeightDimension updateHeight(Long id, int heightValue) {
        // Fetch existing HeightDimension by ID
        HeightDimension existingHeight = heightRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Height not found for id: " + id));

        // Update the height value
        existingHeight.setHeightValue(heightValue);

        // Save the updated HeightDimension
        return heightRepo.save(existingHeight);
    }

    // Update Width
    public WidthDimension updateWidth(Long id, int widthValue) {
        // Fetch existing HeightDimension by ID
        WidthDimension existingWidth = widthRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Width not found for id: " + id));
        // Update the height value
        existingWidth.setWidthValue(widthValue);
        // Save the updated Width Dimension
        return widthRepo.save(existingWidth);
    }

    // Update Price
    public PriceCell updatePrice(int height_id, int width_id, double newPrice) {
        System.out.println(width_id + " width");
        System.out.println(height_id + " height");

        Optional<PriceCell> existingCellOpt = priceCellRepository.findByHeight_IdAndWidth_Id(height_id, width_id);

        if (existingCellOpt.isPresent()) {
            PriceCell existingCell = existingCellOpt.get();
            existingCell.setPrice(newPrice);
            return priceCellRepository.save(existingCell);
        } else {
            throw new RuntimeException("PriceCell not found for height_id " + height_id + " and width_id " + width_id);
        }
    }

    public void addColumnWithZeros(int columnIndex, int rowCount) {
        // Step 1: Insert the new column into the WidthDimension table
        // Check if the column already exists, if not, create it
        Optional<WidthDimension> existingWidth = widthRepo.findByWidthValue(columnIndex);

        if (existingWidth.isPresent()) {
            throw new IllegalArgumentException("Column with width value " + columnIndex + " already exists.");
        }

        // Create and save the new column (WidthDimension)
        WidthDimension newWidth = new WidthDimension(null, columnIndex); // ID will be auto-generated
        widthRepo.save(newWidth);

        // Step 2: Fetch all HeightDimension rows (all rows in the grid)
        List<HeightDimension> heights = heightRepo.findAll();

        if (heights.size() < rowCount) {
            throw new IllegalArgumentException("Row count exceeds the available height dimensions.");
        }

        // Step 3: Create a new PriceCell entry for each row and associate it with the
        // new column
        for (HeightDimension height : heights) {
            // Create a new PriceCell for each row with price 0 for the new column
            PriceCell newPriceCell = new PriceCell();
            newPriceCell.setHeight(height); // Set the height (row)
            newPriceCell.setWidth(newWidth); // Set the new column (width)
            newPriceCell.setPrice(0.0); // Set price to 0

            // Save the new PriceCell
            priceCellRepository.save(newPriceCell);
        }
    }

    public void addRowWithZeros(int rowIndex, int colCount) {

        Optional<HeightDimension> existingHeight = heightRepo.findByHeightValue(rowIndex);

        if (existingHeight.isPresent()) {
            throw new IllegalArgumentException("Rown with height value " + rowIndex + " already exists.");
        }

        // Create and save the new column (WidthDimension)
        HeightDimension newHeight = new HeightDimension(null, rowIndex); // ID will be auto-generated
        heightRepo.save(newHeight);

        // Step 2: Fetch all HeightDimension rows (all rows in the grid)
        List<WidthDimension> widths = widthRepo.findAll();

        if (widths.size() < colCount) {
            throw new IllegalArgumentException("Col count exceeds the available width dimensions.");
        }

        // Step 3: Create a new PriceCell entry for each row and associate it with the
        // new column
        for (WidthDimension width : widths) {
            // Create a new PriceCell for each row with price 0 for the new column
            PriceCell newPriceCell = new PriceCell();
            newPriceCell.setWidth(width); // Set the height (row)
            newPriceCell.setHeight(newHeight); // Set the new column (width)
            newPriceCell.setPrice(0.0); // Set price to 0
            // Save the new PriceCell
            priceCellRepository.save(newPriceCell);
        }

    }

}
