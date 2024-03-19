package com.siki.media.controller;

import com.siki.media.dto.Media;
import com.siki.media.service.MediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@Slf4j
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping("/medias")
    public ResponseEntity<Media> save (@RequestParam("file") MultipartFile multipartFile,
                                       @RequestParam("type") String type) {
        Media media = mediaService.saveOrUpdateFile(multipartFile, "", type);
        return ResponseEntity.ok().body(media);
    }

    @PutMapping("/medias/{id}")
    public ResponseEntity<Media> update (@RequestParam("file")MultipartFile multipartFile,
                                         @PathVariable("id") String id,
                                         @PathVariable("type") String type) {
        Media media = mediaService.saveOrUpdateFile(multipartFile, id, type);
        return ResponseEntity.ok().body(media);
    }

    @GetMapping("/medias/{id}")
    public ResponseEntity<String> get (@PathVariable("id") String id) {
        String url = mediaService.getUrlById(id);
        return ResponseEntity.ok().body(url);
    }

    @DeleteMapping("/medias/{id}")
    public ResponseEntity<Void> delete (@PathVariable("id") String id) {
        mediaService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }
}
