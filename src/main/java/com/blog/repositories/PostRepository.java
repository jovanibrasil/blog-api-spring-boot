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
	

	//@Query(nativeQuery=true, value="SELECT p FROM posts p INNER JOIN users ORDER BY p.last_update_date :orderType Limit :quantity")
	@Query(nativeQuery=true, value="SELECT * FROM posts p LEFT JOIN users u ON u.user_name=p.user_name ORDER BY p.last_update_date")
	Page<Post> findPosts(Pageable pagable);
	@Query(value="SELECT p FROM Post p LEFT JOIN User u ON u.userName=p.author.userName AND "
			+ "u.userName=:user_name ORDER BY p.lastUpdateDate")
	Page<Post> findPostsByUserId(@Param("user_name") String userName, Pageable pagable);
	//@Query(nativeQuery=true, value="SELECT p FROM posts p INNER JOIN users ORDER BY p.last_update_date :orderType Limit :quantity")
	@Query(nativeQuery=true, value="SELECT * FROM posts p LEFT JOIN users u " + 
			"ON u.user_name=p.user_name " + 
			"LEFT JOIN post_tags pt " + 
			"ON pt.post_id=p.post_id " + 
			"WHERE pt.tag=:tag " + 
			"ORDER BY p.last_update_date")
	Page<Post> findPostsByCategory(@Param("tag") String tag, Pageable pagable); 
	
}
