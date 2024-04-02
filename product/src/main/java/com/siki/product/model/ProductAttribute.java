package com.siki.product.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_attribute")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "product_attribute", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductAttributeValue> productAttributeValues = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "product_attribute_set_id")
    private ProductAttributeSet productAttributeSet;
}
