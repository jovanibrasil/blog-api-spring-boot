package com.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.models.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {}
