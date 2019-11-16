package com.blog.repositories;

import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import com.blog.services.impl.FileSystemStorageService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FileSystemStorageTest {

	@Autowired
	private FileSystemStorageService storage;
	
	@Test
	public void saveFile() {
		MultipartFile file = new MockMultipartFile("fileThatDoesNotExists.txt",
	            "fileThatDoesNotExists.txt",
	            "text/plain",
	            "This is a dummy file content".getBytes(StandardCharsets.UTF_8));
		storage.saveImage(file, 1234L);
		
	}
		
	
}
