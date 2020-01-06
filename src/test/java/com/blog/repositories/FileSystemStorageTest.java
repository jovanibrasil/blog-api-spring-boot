package com.blog.repositories;

import com.blog.services.impl.FileSystemStorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FileSystemStorageTest {

	@Autowired
	private FileSystemStorageService storage;
	
	@Test
	public void saveFile() {
		// TODO Fix it
//		MultipartFile file = new MockMultipartFile("fileThatDoesNotExists.txt",
//	            "fileThatDoesNotExists.txt",
//	            "text/plain",
//	            "This is a dummy file content".getBytes(StandardCharsets.UTF_8));
//		storage.saveImage(file, 1234L);
//		
	}
		
	
}
