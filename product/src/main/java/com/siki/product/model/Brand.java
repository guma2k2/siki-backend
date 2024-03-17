package com.siki.product.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "brand")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 60, unique = true, nullable = false)
    private String name;

    private String logo;
}
