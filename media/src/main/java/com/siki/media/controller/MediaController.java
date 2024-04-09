package com.siki.media.controller;

import com.siki.media.dto.MediaDto;
import com.siki.media.service.MediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping("/create")
    public ResponseEntity<MediaDto> save (@RequestParam("file") MultipartFile multipartFile,
                                          @RequestParam("type") String type) {
        MediaDto media = mediaService.create(multipartFile, type);
        return ResponseEntity.ok().body(media);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MediaDto> updateUrl (@RequestParam("file")MultipartFile multipartFile,
                                               @PathVariable("id") String id,
                                               @RequestParam("type") String type
    ) {
        MediaDto media = mediaService.updateUrl(multipartFile, id, type);
        return ResponseEntity.ok().body(media);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MediaDto> updateStatus (
                                            @PathVariable("id") String id,
                                            @RequestParam("status") boolean status) {
        MediaDto media = mediaService.updateStatus(id, status);
        return ResponseEntity.ok().body(media);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getUrlById(@PathVariable("id") String id) {
        String url = mediaService.getUrlById(id);
        return ResponseEntity.ok().body(url);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable("id") String id) {
        mediaService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }
}
