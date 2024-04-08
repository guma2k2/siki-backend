package com.siki.media.service;

import com.siki.media.dto.MediaDto;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    MediaDto create (MultipartFile multipartFile, String type);
    MediaDto updateUrl (MultipartFile multipartFile, String uuid, String type);

    MediaDto updateStatus (String uuid, boolean status);
    String getUrlById (String uuid);
    void deleteFile (String uuid);
}
