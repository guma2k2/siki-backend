package com.siki.product.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, unique = true, nullable = false)

    private String name;

    @Column(length = 512)
    private String description;
    private int quantity;
    private boolean status;

    private Long storeId;

    private ProductPrice productPrice;
}
