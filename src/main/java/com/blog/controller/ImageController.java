package com.blog.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.model.Image;
import com.blog.services.ImageService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

	private final ImageService imageService;
	
	@ApiOperation(value = "Busca imagem por ID.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Imagem encontrada e retornado."),
		@ApiResponse(code = 404, message = "Imagem não encontrada.")})
	@GetMapping("/{imageId}")
	public ResponseEntity<byte[]> getImage(@PathVariable Long imageId) { 
		log.info("Getting image {}", imageId);
		Image image = imageService.findImageById(imageId);
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image.getBytes());
	}
	
}
