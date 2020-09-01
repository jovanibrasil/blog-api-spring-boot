package com.blog.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.blog.exception.ExceptionMessages;
import com.blog.exception.NotFoundException;
import com.blog.model.Image;
import com.blog.repositories.ImageRepository;
import com.blog.services.ImageService;
import com.blog.utils.ImageUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

	private final ImageRepository imageRepository;
	
	/**
	 * Saves an image.
	 * 
	 */
	@Override
	public Image saveImage(Image image) {
		image.setBytes(ImageUtils.compressBytes(image.getBytes()));
		imageRepository.save(image);
		image.setBytes(ImageUtils.decompressBytes(image.getBytes()));
		return image;
	}

	/**
	 * Retrieves an image by ID. 
	 */
	@Override
	public Image findImageById(Long imageId) {
		Optional<Image> optImage = imageRepository.findById(imageId);
		if(optImage.isPresent()) {
			Image image = optImage.get();
			image.setBytes(ImageUtils.decompressBytes(image.getBytes()));
			return image;
		}
		throw new NotFoundException(ExceptionMessages.IMAGE_NOT_FOUND);
	}
	
}
