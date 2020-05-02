package com.blog.repositories;

import com.blog.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
	@Query(value="SELECT p FROM Post p")
	Page<Post> findAll(Pageable pagable);
	
	@Query(value="SELECT p FROM Post p WHERE p.author.userName = ?1")
	Page<Post> findByUserName(String userName, Pageable pagable);
	
	@Query(value="SELECT p FROM Post p WHERE :tag MEMBER OF p.tags")
	Page<Post> findByCategory(@Param("tag") String tag, Pageable pagable); 
	
	@Query(value="SELECT p FROM Post p WHERE p.title LIKE %:term% OR p.body LIKE %:term%")
	Page<Post> findByTerm(@Param("term") String term, Pageable pageable);
	
}
