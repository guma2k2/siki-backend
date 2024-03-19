package com.siki.media.service;

import com.siki.media.dto.Media;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    Media saveOrUpdateFile (MultipartFile multipartFile, String uuid, String type);
    String getUrlById (String uuid);
    void deleteFile (String uuid);
}
