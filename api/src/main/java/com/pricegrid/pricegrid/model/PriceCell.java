package com.pricegrid.pricegrid.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceCell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "width_id")
    private WidthDimension width;

    @ManyToOne
    @JoinColumn(name = "height_id")
    private HeightDimension height;

    private double price;
}
