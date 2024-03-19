package com.siki.media.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Media {
    private String id;

    private String url;

    private String duration;

}
