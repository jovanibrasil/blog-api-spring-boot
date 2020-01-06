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
		Path directoryPath = Paths.get(this.blogFilesDir, this.imagesDir, String.valueOf(postId));
		this.deleteDirectory(directoryPath);
	}
	
	public void deleteImage(String fileName, Long postId) {
		Path directoryPath = Paths.get(this.blogFilesDir, this.imagesDir, String.valueOf(postId));
		this.deleteFile(directoryPath, fileName);
	}
	
	public void saveImage(MultipartFile file, Long postId) {
		Path directoryPath = Paths.get(this.blogFilesDir, this.imagesDir, String.valueOf(postId));
		this.saveFile(directoryPath, file);
	}
	
	public void saveFile(Path directoryPath, MultipartFile file) {
		
		log.info("Saving image. Name: {} Original name: {} ", file.getName(), file.getOriginalFilename());
		Path filePath = directoryPath.resolve(file.getOriginalFilename());
		
		try {
			Files.createDirectories(directoryPath);
			file.transferTo(filePath.toFile());
			log.info("Image {} saved at {}", file.getName(), directoryPath.toString());
		} catch (Exception e) {
			throw new RuntimeException("It was not possible to save the file." + e.getMessage());
		}
		
	}

	public void deleteFile(Path directoryPath, String fileName) {
		Path filePath = directoryPath.resolve(fileName);
		try {
			Files.deleteIfExists(filePath);
			log.info("Image {} deleted from {}", fileName, directoryPath.toString());
		} catch (Exception e) {
			throw new RuntimeException("It was not possible to delete the file." + e.getMessage());
		}
	}

	public void deleteDirectory(Path path) {
		try {
			FileUtils.deleteDirectory(new File(path.toUri()));
		} catch (Exception e) {
			throw new RuntimeException("It was not possible to delete the directory." + e.getMessage());
		}
	}

}
