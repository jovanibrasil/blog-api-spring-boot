package com.blog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.blog.models.Post;

@Transactional(readOnly=true)
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
	@Query(value="SELECT p FROM Post p")
	Page<Post> findPosts(Pageable pagable);
	
	@Query(value="SELECT p FROM Post p WHERE p.author.userId = ?1")
	Page<Post> findPostsByUserId(Long userId, Pageable pagable);
	
	@Query(value="SELECT p FROM Post p WHERE :tag MEMBER OF p.tags")
	Page<Post> findPostsByCategory(@Param("tag") String tag, Pageable pagable); 
	
}
