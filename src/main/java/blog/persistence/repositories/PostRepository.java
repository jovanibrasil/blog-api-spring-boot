package blog.persistence.repositories;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import blog.presentation.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
	@Query("SELECT p FROM Post p LEFT JOIN FETCH p.author ORDER BY p.lastUpdateDate DESC")
	List<Post> findLatest5Posts(Pageable pageRequest);
	
	@Query("SELECT p FROM Post p WHERE p.author.userId=:userId")
	List<Post> findPostsByUserId(@Param("userId") Long userId);
	
}

