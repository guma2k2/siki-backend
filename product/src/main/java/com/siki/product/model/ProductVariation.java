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
    private Long id;

    private boolean isDefault;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_attribute_value_id")
    private ProductAttributeValue productAttributeValue;
}
