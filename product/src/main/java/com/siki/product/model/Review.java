package com.siki.product.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "cart")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private int ratingStar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String customerId;

    @CreatedDate
    @Column(name = "created_at")
    protected LocalDateTime createdAt;


    @LastModifiedDate
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;
}
