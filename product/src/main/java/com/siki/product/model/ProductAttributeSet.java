package com.siki.product.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "product_attribute_set")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAttributeSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductAttribute> productAttributes = new ArrayList<>();
}
