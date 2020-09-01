package com.blog.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.blog.ScenarioFactory;
import com.blog.exception.NotFoundException;
import com.blog.services.ImageService;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ImageControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ImageService imageService;
	
	@Test
	public void findExistentImage() throws Exception {
		when(imageService.findImageById(0L)).thenReturn(ScenarioFactory.getDecompressedImage());
		mvc.perform(MockMvcRequestBuilders.get("/images/"+ 0))
			.andExpect(content().contentType(MediaType.IMAGE_JPEG))
			.andExpect(status().isOk());
	}
	
	@Test
	public void findInexistentImage() throws Exception {
		when(imageService.findImageById(0L)).thenThrow(new NotFoundException("error.image.notfound"));
		mvc.perform(MockMvcRequestBuilders.get("/images/"+ 0))
			.andExpect(status().isNotFound());
	}
	
}
