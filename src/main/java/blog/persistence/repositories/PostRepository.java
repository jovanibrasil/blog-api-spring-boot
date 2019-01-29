package blog.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import blog.enums.ListOrderType;
import blog.presentation.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
	//@Query(nativeQuery=true, value="SELECT p FROM posts p INNER JOIN users ORDER BY p.last_update_date :orderType Limit :quantity")
	@Query(nativeQuery=true, value="SELECT * FROM posts p LEFT JOIN users u WHERE u.user_id=p.user_id ORDER BY p.last_update_date DESC Limit :limit")
	List<Post> findPosts(@Param("limit") Long limit);
	
	@Query(nativeQuery=true, value="SELECT * FROM posts p LEFT JOIN users u WHERE u.user_id=p.user_id AND u.user_id=:user_id ORDER BY p.last_update_date DESC Limit :limit")
	List<Post> findPostsByUserId(@Param("user_id") Long userId, @Param("limit") Long limit);
	
}

