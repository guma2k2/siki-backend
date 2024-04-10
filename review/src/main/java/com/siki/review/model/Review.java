package com.siki.review.model;

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
    private Long productId;
    private String customerId;
    private Long baseProductId;

    @CreatedDate
    @Column(name = "created_at")
    protected LocalDateTime createdAt;


    @LastModifiedDate
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;
}
