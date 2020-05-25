package com.blog.services;

import com.blog.model.Image;

public interface ImageService {
	Image saveImage(Image image);
	Image findImageById(Long imageId);
}
