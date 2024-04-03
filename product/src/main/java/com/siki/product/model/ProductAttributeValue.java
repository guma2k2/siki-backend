package com.siki.product.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_attribute_value")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAttributeValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_attribute_id")
    private ProductAttribute productAttribute;

    @OneToMany(mappedBy = "productAttributeValue", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductVariation> productVariations = new ArrayList<>();
}
