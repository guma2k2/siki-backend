package com.siki.product.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_variation")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductVariation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_value_id")
    private ProductOptionValue productOptionValue;

    private int quantity;

    private Double price;
}
