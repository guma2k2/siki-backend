package com.siki.media.model;

import com.siki.media.enums.MediaEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "media")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Media {

    @Id
    private String id;

    private String url;

    @Builder.Default
    private boolean status = false;

    private String duration;

    @Enumerated(EnumType.STRING)
    private MediaEnum type;
}
