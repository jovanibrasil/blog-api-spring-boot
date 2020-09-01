package com.blog.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.model.Image;
import com.blog.services.ImageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

	private final ImageService imageService;
	
	@GetMapping("/{imageId}")
	public ResponseEntity<byte[]> getImage(@PathVariable Long imageId) { 
		log.info("Getting image {}", imageId);
		Image image = imageService.findImageById(imageId);
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image.getBytes());
	}
	
}
