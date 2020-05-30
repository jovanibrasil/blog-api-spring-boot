package com.blog.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.blog.exception.NotFoundException;
import com.blog.model.Image;
import com.blog.repositories.ImageRepository;
import com.blog.services.ImageService;

import lombok.RequiredArgsConstructor;

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

	/**
	 * Retorna uma imagem pelo seu ID. A imagem retornada é um array de bytes usando 
	 * esquema de codificação base64.
	 * 
	 */
	@Override
	public Image findImageById(Long imageId) {
		Optional<Image> optImage = imageRepository.findById(imageId);
		if(optImage.isPresent()) {
			Image image = optImage.get();
			//image.setBytes(ImageUtils.decompressBytes(image.getBytes()));
			//byte[] decodedBytes = Base64.getMimeDecoder().decode(image.getBytes());
			//log.info("{}", new String(image.getBytes()));
			return image;
		}
		throw new NotFoundException("Imagem não econtrada");
	}	
	
}
