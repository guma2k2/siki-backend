package com.siki.media.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.siki.media.MediaRepository;
import com.siki.media.dto.MediaDto;
import com.siki.media.enums.MediaEnum;
import com.siki.media.exception.BadRequestException;
import com.siki.media.exception.NotFoundException;
import com.siki.media.model.Media;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MediaServiceImpl implements MediaService {

    private final Cloudinary cloudinary;

    private final MediaRepository mediaRepository;

    private final String VideoType = "VIDEO";

    private final String ImageType = "IMAGE";

    public MediaServiceImpl(Cloudinary cloudinary, MediaRepository mediaRepository) {
        this.cloudinary = cloudinary;
        this.mediaRepository = mediaRepository;
    }

    @Override
    public MediaDto create(MultipartFile multipartFile, String type) {
        HashMap<String, String> map = new HashMap<>();
        String fileId = UUID.randomUUID().toString();
        map.put("public_id", fileId);
        map.put("resource_type", "auto");
        Map uploadResult = null;
        try {
            uploadResult = cloudinary.uploader()
                    .upload(multipartFile.getBytes(), map);
        } catch (IOException e) {
            throw new BadRequestException(e.getMessage());
        }
        String url = uploadResult
                .get("url")
                .toString();
        Media media = Media.builder()
                .id(fileId)
                .status(false)
                .url(url)
                .type(MediaEnum.valueOf(type))
                .build();
        if (type.equals(VideoType)) {
            String duration = uploadResult.get("duration").toString();
            media.setDuration(duration);
        }

        mediaRepository.saveAndFlush(media);

        return MediaDto.fromModel(media);
    }

    @Override
    public MediaDto updateUrl(MultipartFile multipartFile, String uuid, String type) {
        Media media = mediaRepository.findById(uuid).orElseThrow(() ->  new NotFoundException("Media was not found"));
        HashMap<String, String> map = new HashMap<>();
        map.put("public_id", media.getId());
        map.put("resource_type", "auto");
        Map uploadResult = null;
        try {
            uploadResult = cloudinary.uploader()
                    .upload(multipartFile.getBytes(), map);
        } catch (IOException e) {
            throw new BadRequestException(e.getMessage());
        }
        String url = uploadResult
                .get("url")
                .toString();
        media.setUrl(url);
        if (type.equals(VideoType)) {
            String duration = uploadResult.get("duration").toString();
            media.setDuration(duration);
            media.setType(MediaEnum.VIDEO);
        }else {
            media.setType(MediaEnum.IMAGE);
        }
        mediaRepository.save(media);
        return MediaDto.fromModel(media);    }

    @Override
    public MediaDto updateStatus(String uuid, boolean status) {
        Media media = mediaRepository.findById(uuid).orElseThrow(() ->  new NotFoundException("Media was not found"));
        media.setStatus(status);
        return MediaDto.fromModel(mediaRepository.saveAndFlush(media));
    }

    @Override
    public String getUrlById(String uuid) {
        return cloudinary.url() // Add any transformations if needed
                .generate(uuid);
    }

    @Override
    public void deleteFile(String uuid) {
        try {
            cloudinary.uploader().destroy(uuid, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
