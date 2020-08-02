package com.blog.services.impl;


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.blog.ScenarioFactory;
import com.blog.model.Image;
import com.blog.repositories.ImageRepository;
import com.blog.services.ImageService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ImageServiceTest {

	@Autowired
	private ImageService imageService;
	
	@MockBean
	private ImageRepository imageRepository;
	
	private Image decompressedImage = ScenarioFactory.getDecompressedImage();
	private Image compressedImage = ScenarioFactory.getCompressedImage();
	
	@Test
	public void testSaveImage() {
		when(imageRepository.save(any())).thenAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock image) throws Throwable {
				Image img = image.getArgument(0);
				img.setId(1L);
				return img;
			}
		});
		Image savedImage = imageService.saveImage(decompressedImage);
		assertEquals(1L, savedImage.getId().longValue());
	}
	
	@Test
	public void testFindImageById() {
		when(imageRepository.findById(decompressedImage.getId())).thenReturn(Optional.of(compressedImage));
		Image savedImage = imageService.findImageById(decompressedImage.getId());
		assertEquals(decompressedImage, savedImage);
	}
	
}
