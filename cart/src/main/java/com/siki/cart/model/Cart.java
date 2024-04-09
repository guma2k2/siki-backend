package com.siki.cart.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private String userId;
    private int quantity;
    private boolean selected;
}
