package com.blog.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.blog.exceptions.NotFoundException;
import com.blog.models.Image;
import com.blog.repositories.ImageRepository;
import com.blog.services.ImageService;
import com.blog.utils.ImageUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

	private final ImageRepository imageRepository;
	
	@Override
	public Image saveImage(Image image) {
		//ImageUtils.compressBytes(image.getBytes())
		//String encodedString = ImageUtils.encodeBase64(image.getBytes());
		//System.out.println(encodedString);		
		//image.setBytes(ImageUtils.compressBytes(image.getBytes()));
		return imageRepository.save(image);
	}

	@Override
	public Image findImageById(Long imageId) {
		Optional<Image> optImage = imageRepository.findById(imageId);
		if(optImage.isPresent()) {
			Image image = optImage.get();
			//image.setBytes(ImageUtils.decompressBytes(image.getBytes()));
			return image;
		}
		throw new NotFoundException("Imagem não econtrada");
	}	
	
}
