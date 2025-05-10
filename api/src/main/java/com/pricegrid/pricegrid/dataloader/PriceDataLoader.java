package com.pricegrid.pricegrid.dataloader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pricegrid.pricegrid.model.*;
import com.pricegrid.pricegrid.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.util.*;

@Component
@RequiredArgsConstructor
public class PriceDataLoader implements CommandLineRunner {

    private final WidthDimensionRepository widthRepo;
    private final HeightDimensionRepository heightRepo;
    private final PriceCellRepository priceRepo;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = getClass().getResourceAsStream("/prices.json");

        PriceGridData data = mapper.readValue(inputStream, PriceGridData.class);

        Map<Integer, WidthDimension> widthMap = new HashMap<>();
        for (Integer w : data.getWidths()) {
            WidthDimension wd = new WidthDimension();
            wd.setWidthValue(w);
            widthMap.put(w, widthRepo.save(wd));
        }

        Map<Integer, HeightDimension> heightMap = new HashMap<>();
        for (Integer h : data.getHeights()) {
            HeightDimension hd = new HeightDimension();
            hd.setHeightValue(h);
            heightMap.put(h, heightRepo.save(hd));
        }

        List<PriceCell> cells = new ArrayList<>();
        for (int i = 0; i < data.getHeights().size(); i++) {
            for (int j = 0; j < data.getWidths().size(); j++) {
                PriceCell cell = new PriceCell();
                cell.setHeight(heightMap.get(data.getHeights().get(i)));
                cell.setWidth(widthMap.get(data.getWidths().get(j)));
                cell.setPrice(data.getPrices().get(i).get(j));
                cells.add(cell);
            }
        }

        priceRepo.saveAll(cells);
        System.out.println("Price grid data loaded successfully.");
    }

    // DTO Class
    public static class PriceGridData {
        private List<Integer> widths;
        private List<Integer> heights;
        private List<List<Double>> prices;

        public List<Integer> getWidths() { return widths; }
        public void setWidths(List<Integer> widths) { this.widths = widths; }

        public List<Integer> getHeights() { return heights; }
        public void setHeights(List<Integer> heights) { this.heights = heights; }

        public List<List<Double>> getPrices() { return prices; }
        public void setPrices(List<List<Double>> prices) { this.prices = prices; }
    }
}
