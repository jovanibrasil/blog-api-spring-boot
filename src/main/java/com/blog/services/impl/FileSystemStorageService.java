package com.blog.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class FileSystemStorageService {

	@Value("${filesystem.blog}")	
	private String blogFilesDir;
	@Value("${filesystem.blog.images}")	
	private String imagesDir;
	
	public void deletePostDirectory(Long postId) {
		Path directoryPath = Paths.get(this.blogFilesDir, imagesDir, String.valueOf(postId));
		this.deleteDirectory(directoryPath);
	}
	
	public void deletePostImage(String fileName, Long postId) {
		this.deleteFile(fileName, postId);
	}
	
	public void saveImage(MultipartFile file, Long postId) {
		this.save(this.imagesDir, file, postId);
	}
	
	private void save(String dir, MultipartFile file, Long postId) {
		
		log.info("Saving image. Name: {} Original name: {} ", file.getName(), file.getOriginalFilename());
		
		Path directoryPath = Paths.get(this.blogFilesDir, dir, String.valueOf(postId));
		Path filePath = directoryPath.resolve(file.getOriginalFilename());
		
		try {
			Files.createDirectories(directoryPath);
			file.transferTo(filePath.toFile());
			log.info("Image {} saved at {}", file.getName(), directoryPath.toString());
		} catch (Exception e) {
			throw new RuntimeException("...");
		}
		
	}
	
	private void deleteFile(String fileName, Long postId) {
		Path directoryPath = Paths.get(this.blogFilesDir, imagesDir, String.valueOf(postId));
		Path filePath = directoryPath.resolve(fileName);
		try {
			Files.deleteIfExists(filePath);
			log.info("Image {} deleted from {}", fileName, directoryPath.toString());
		} catch (Exception e) {
			throw new RuntimeException("...");
		}
	}
		
	private void deleteDirectory(Path path) {
		try {
			FileUtils.deleteDirectory(new File(path.toUri()));
		} catch (Exception e) {
			throw new RuntimeException("...");
		}
	}

}
