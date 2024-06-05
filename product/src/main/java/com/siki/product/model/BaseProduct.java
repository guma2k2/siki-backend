package com.siki.product.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "base_product")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, nullable = false)
    private String name;

    @Column(length = 128, nullable = false, unique = true)
    private String slug;

    @Column(length = 512)
    private String description;

    private boolean status;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @OneToMany(mappedBy = "baseProduct", cascade = {CascadeType.PERSIST})
    List<ProductAttribute> productAttributes = new ArrayList<>();

    @OneToMany(mappedBy = "baseProduct", cascade = {CascadeType.PERSIST})
    List<Product> products = new ArrayList<>();

    private Integer storeId;
}
