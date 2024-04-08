package com.siki.media.dto;

import com.siki.media.model.Media;
import lombok.*;

public record MediaDto (
        String id,
        String url,
        String type,
        boolean status,
        String duration
) {
    public static MediaDto fromModel(Media media) {
        return new MediaDto(media.getId(), media.getUrl(), media.getType().toString(), media.isStatus() ,media.getDuration());
    }
}
