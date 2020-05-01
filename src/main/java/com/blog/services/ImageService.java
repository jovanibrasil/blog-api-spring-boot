package com.blog.services;

import com.blog.models.Image;

public interface ImageService {
	Image saveImage(Image image);
	Image findImageById(Long imageId);
}
