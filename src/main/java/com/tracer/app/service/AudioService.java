package com.tracer.app.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.time.Instant;

@Service
public class AudioService {
    public String saveAudio(MultipartFile multipartFile, String deviceId){
        String upload = System.getProperty("user.dir");
        upload += File.separator + "mp3" + File.separator + "call";

        File dir = new File(upload);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        if (!multipartFile.isEmpty()) {
            try {
                upload += File.separator + deviceId + "_" + Instant.now().getEpochSecond() + multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
                File newFile = new File(upload);

                FileOutputStream fileOutputStream = new FileOutputStream(newFile);
                fileOutputStream.write(multipartFile.getBytes());
                fileOutputStream.close();
            }catch (Exception e) {
                return null;
            }
        }else{
            return null;
        }

        return upload;
    }
}
