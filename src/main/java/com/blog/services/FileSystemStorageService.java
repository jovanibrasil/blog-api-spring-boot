package com.blog.services;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileSystemStorageService {

    void deleteDirectory(Path path);
    void deleteFile(Path directoryPath, String fileName);
    void saveFile(Path directoryPath, MultipartFile file);
    void saveImage(MultipartFile file, Long postId);
    void deleteImage(String fileName, Long postId);
    void deletePostDirectory(Long postId);

}
