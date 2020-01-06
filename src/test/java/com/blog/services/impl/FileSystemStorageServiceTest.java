package com.blog.services.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FileSystemStorageServiceTest {

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @Test
    public void deletePostDirectory() {
        this.fileSystemStorageService.deletePostDirectory(1L);
    }

    @Test
    public void saveImage() {
        MultipartFile mockMulti = new MockMultipartFile("banner", "orig", null, "file".getBytes());
        this.fileSystemStorageService.saveImage(mockMulti, 1L);
    }

    @Test(expected = RuntimeException.class)
    public void saveNullImage() {
        this.fileSystemStorageService.saveImage(null, 1L);
    }

}